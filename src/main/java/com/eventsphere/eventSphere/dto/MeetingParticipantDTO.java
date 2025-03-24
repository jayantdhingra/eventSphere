package com.eventsphere.eventSphere.dto;

import java.time.LocalDateTime;

public class MeetingParticipantDTO {
    private Long userId;
    private String username;
    private String email;
    private String role; // HOST, ATTENDEE
    private boolean isMuted;
    private boolean isVideoOn;
    private LocalDateTime joinedAt;

    public MeetingParticipantDTO(Long userId, String username, String email, String role, boolean isMuted, boolean isVideoOn, LocalDateTime joinedAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.isMuted = isMuted;
        this.isVideoOn = isVideoOn;
        this.joinedAt = joinedAt;
    }

}

