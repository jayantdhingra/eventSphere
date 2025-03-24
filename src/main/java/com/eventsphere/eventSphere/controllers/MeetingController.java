package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.dto.MeetingParticipantDTO;
import com.eventsphere.eventSphere.entity.ChatRequest;
import com.eventsphere.eventSphere.entity.Meeting;
import com.eventsphere.eventSphere.entity.User;
import com.eventsphere.eventSphere.repository.UserRepository;
import com.eventsphere.eventSphere.service.MeetingParticipantService;
import com.eventsphere.eventSphere.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/meeting")
@Slf4j
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingParticipantService meetingParticipantsService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createMeeting(@RequestBody Map<String, Long> request) {
        Long hostId = request.get("hostId");
        if (hostId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Host ID is required"));
        }

        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("inside create meeting");

        String meetingId = meetingService.createMeeting(host);
        return ResponseEntity.ok(Map.of("meetingId", meetingId));
    }

    @MessageMapping("/join")
    @SendTo("/topic/meetings")
    public String joinMeeting(String meetingId) {

        return meetingId + " joined!";
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<List<MeetingParticipantDTO>> getMeetingParticipants(@PathVariable String meetingId) {
        List<MeetingParticipantDTO> participants = meetingParticipantsService.getParticipantsByMeetingId(meetingId);
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        log.info("Meeting service is healthy");
        return "OK";
    }
}
