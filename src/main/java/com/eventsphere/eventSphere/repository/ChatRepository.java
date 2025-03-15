package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
   // List<ChatMessage> findBySenderIdAndReceiverIdOrderByTimestampAsc(Long senderId, Long receiverId);

//    @Query("SELECT c FROM ChatMessage c WHERE (c.senderId = :senderId AND c.receiverId = :receiverId) OR (c.senderId = :receiverId AND c.receiverId = :senderId) ORDER BY c.timestamp ASC")
//    List<ChatMessage> findChatBetweenUsers(Long senderId, Long receiverId);

    @Query("SELECT c FROM ChatMessage c WHERE (c.senderId = :user1 AND c.receiverId = :user2) OR (c.senderId = :user2 AND c.receiverId = :user1) ORDER BY c.timestamp ASC")
    List<ChatMessage> findChatBetweenUsers(@Param("user1") Long user1, @Param("user2") Long user2);

}
