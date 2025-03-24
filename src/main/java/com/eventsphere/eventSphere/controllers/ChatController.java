package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.repository.ChatRepository;
import com.eventsphere.eventSphere.repository.UserRepository;
import com.eventsphere.eventSphere.repository.ChatRequestRepository;
import com.eventsphere.eventSphere.entity.ChatRequest;
import com.eventsphere.eventSphere.entity.ChatMessage;
import com.eventsphere.eventSphere.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRequestRepository chatRequestRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Sender sends a chat request to the receiver.
     */
    @PostMapping("/request")
    public String sendChatRequest(@RequestBody Map<String, String> requestBody) {
        String senderId = requestBody.get("senderId");
        String receiverId = requestBody.get("receiverId");

        Optional<User> sender = userRepository.findById(Long.valueOf(senderId));
        Optional<User> receiver = userRepository.findById(Long.valueOf(receiverId));

        if (sender.isEmpty() || receiver.isEmpty()) {
            log.warn("Invalid sender or receiver ID. Sender ID: {}, Receiver ID: {}", senderId, receiverId);
            return "Invalid sender or receiver ID.";
        }

        // 🔹 Prevent sending a request if a chat is already ACCEPTED
        Optional<ChatRequest> existingAcceptedRequest = chatRequestRepository
                .findBySenderIdAndReceiverIdAndStatus(Long.valueOf(senderId), Long.valueOf(receiverId), "ACCEPTED");
        if (existingAcceptedRequest.isPresent()) {
            return "Chat request already accepted. You can start chatting.";
        }

        Optional<ChatRequest> existingRequest = chatRequestRepository
                .findBySenderIdAndReceiverIdAndStatus(Long.valueOf(senderId), Long.valueOf(receiverId), "PENDING");

        if (existingRequest.isPresent()) {
            return "A pending chat request already exists.";
        }

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setSender(sender.get());
        chatRequest.setReceiver(receiver.get());
        chatRequest.setStatus("PENDING");
        chatRequest.setRequestTime(LocalDateTime.now());
        chatRequestRepository.save(chatRequest);

        // 🔹 WebSocket Notification for Pending Requests
        long pendingCount = chatRequestRepository.countByReceiverIdAndStatus(Long.valueOf(receiverId), "PENDING");
        messagingTemplate.convertAndSend("/topic/pendingCount/" + receiverId, pendingCount);

        log.info("Chat request sent from {} to {}", sender.get().getUserName(), receiver.get().getUserName());
        return "Chat request sent successfully.";
    }


    @GetMapping("/checkAllPendingRequests/{receiverId}")
    public List<ChatRequest> getAllChatRequests(@PathVariable String receiverId){
        return chatRequestRepository.findByReceiverIdAndStatus(Long.valueOf(receiverId),"PENDING");
    }

    /**
     * Receiver accepts the chat request.
     */
    @PostMapping("/request/accept")
    public String acceptChatRequest(@RequestParam Long requestId) {
        Optional<ChatRequest> chatRequestOpt = chatRequestRepository.findById(requestId);

        if (chatRequestOpt.isEmpty()) {
            log.warn("Chat request with ID {} not found.", requestId);
            return "Chat request not found.";
        }

        ChatRequest chatRequest = chatRequestOpt.get();
        chatRequest.setStatus("ACCEPTED");
        chatRequest.setResponseTime(LocalDateTime.now());
        chatRequestRepository.save(chatRequest);

        Long chatId = chatRequest.getId();

        // 🔹 WebSocket Notification for Chat Acceptance
        messagingTemplate.convertAndSend("/topic/chat/" + chatRequest.getSender().getId(), Map.of("chatId", chatId, "message", "Your chat request was accepted!"));
        messagingTemplate.convertAndSend("/topic/chat/" + chatRequest.getReceiver().getId(), Map.of("chatId", chatId, "message", "Chat request accepted!"));

        log.info("Chat request between {} and {} accepted.", chatRequest.getSender().getUserName(), chatRequest.getReceiver().getUserName());
        return "Chat request accepted.";
    }


    /**
     * Send a chat message after chat request acceptance.
     */
    @PostMapping("/send")
    public String sendMessage(@RequestBody ChatMessage chatMessageRequest) {
        Long senderId = chatMessageRequest.getSenderId();
        Long receiverId = chatMessageRequest.getReceiverId();
        String content = chatMessageRequest.getContent();

        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);

        if (sender.isEmpty() || receiver.isEmpty()) {
            log.warn("Invalid sender or receiver ID. Sender ID: {}, Receiver ID: {}", senderId, receiverId);
            return "Invalid sender or receiver ID.";
        }

        // 🔹 Check if chat is ACCEPTED in either direction
        Optional<ChatRequest> acceptedRequest = chatRequestRepository
                .findBySenderIdAndReceiverIdAndStatus(senderId, receiverId, "ACCEPTED");
        if (acceptedRequest.isEmpty()) {
            acceptedRequest = chatRequestRepository.findBySenderIdAndReceiverIdAndStatus(receiverId, senderId, "ACCEPTED");
        }

        if (acceptedRequest.isEmpty()) {
            log.warn("No accepted chat request found between sender ID {} and receiver ID {}.", senderId, receiverId);
            return "Chat request not accepted yet.";
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(senderId);
        chatMessage.setReceiverId(receiverId);
        chatMessage.setSender(sender.get().getUserName());
        chatMessage.setContent(content);
        chatMessage.setTimestamp(LocalDateTime.now());
        chatRepository.save(chatMessage);

        // 🔹 WebSocket: Notify Receiver
        messagingTemplate.convertAndSend("/topic/chat/" + receiverId, chatMessage);

        log.info("Message sent from {} to {}", sender.get().getUserName(), receiver.get().getUserName());
        return "Message sent successfully.";
    }



    @GetMapping("/chatId")
    public ResponseEntity<?> getChatId(@RequestParam Long senderId, @RequestParam Long receiverId) {
        Optional<ChatRequest> chatRequest = chatRequestRepository
                .findBySenderIdAndReceiverIdAndStatus(senderId, receiverId, "ACCEPTED");

        if (chatRequest.isEmpty()) {
            chatRequest = chatRequestRepository.findByReceiverIdAndSenderIdAndStatus(senderId, receiverId, "ACCEPTED");
        }

        if (chatRequest.isEmpty()) {
            log.warn("No active chat found between {} and {}", senderId, receiverId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat request not accepted.");
        }

        Long chatId = chatRequest.get().getId();
        log.info("Chat ID {} found between {} and {}", chatId, senderId, receiverId);
        return ResponseEntity.ok(Map.of("chatId", chatId));
    }


    @PostMapping("/acknowledge")
    public String acknowledgeMessage(@RequestParam Long messageId) {
        Optional<ChatMessage> chatMessageOpt = chatRepository.findById(messageId);

        if (chatMessageOpt.isEmpty()) {
            log.warn("Message with ID {} not found.", messageId);
            return "Message not found.";
        }

        ChatMessage chatMessage = chatMessageOpt.get();
        Long receiverId=chatMessage.getReceiverId();
        chatMessage.setAcknowledged(true);  // ✅ Mark the message as acknowledged
        chatMessage.setAcknowledgedTime(LocalDateTime.now());  // ✅ Save acknowledgment time
        chatRepository.save(chatMessage);

        // 🔹 WebSocket: Notify the sender that the message was read
        messagingTemplate.convertAndSend("/topic/chat/ack/" + chatMessage.getSenderId(),
                "Message " + messageId + " read by receiver " + receiverId);

        log.info("Message ID {} acknowledged by {}", messageId, receiverId);
        return "Message acknowledged successfully.";
    }

    @GetMapping("/conversation")
    public List<ChatMessage> viewMessages(@RequestParam Long senderId, @RequestParam Long receiverId) {
        List<ChatMessage> messages = chatRepository.findChatBetweenUsers(senderId, receiverId);

        // Notify sender that receiver has viewed messages
        messagingTemplate.convertAndSend("/topic/chat/viewed/" + senderId,
                "User " + receiverId+ " viewed the chat");

        log.info("Fetching conversation between {} and {}", senderId,receiverId);
        return messages;
    }



    /**
     * Health check endpoint.
     */
    @GetMapping("/health-check")
    public String healthCheck() {
        log.info("Chat service is healthy");
        return "OK";
    }
}
