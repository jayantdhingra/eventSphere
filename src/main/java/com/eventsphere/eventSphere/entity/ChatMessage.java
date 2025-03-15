package com.eventsphere.eventSphere.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendeeChats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chat_id;

    private String content;
    private String sender;
    private Long senderId;
    private Long receiverId;
    private LocalDateTime timestamp;
    private Boolean acknowledged = false;
    private LocalDateTime acknowledgedTime;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonBackReference(value = "conference-chats")
    private Conference conference;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }
}
