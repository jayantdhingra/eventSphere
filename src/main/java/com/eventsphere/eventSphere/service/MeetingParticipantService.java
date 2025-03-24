package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.dto.MeetingParticipantDTO;
import com.eventsphere.eventSphere.entity.MeetingParticipants;
import com.eventsphere.eventSphere.entity.User;
import com.eventsphere.eventSphere.repository.MeetingRepository;
import com.eventsphere.eventSphere.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingParticipantService {
        @Autowired
        private ParticipantRepository meetingParticipantRepository;

        @Autowired
        private MeetingRepository meetingRepository;

    public List<MeetingParticipantDTO> getParticipantsByMeetingId(String meetingId) {
            if (!meetingRepository.existsByMeetingId(meetingId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found.");
            }

            List<MeetingParticipants> participants = meetingParticipantRepository.findByMeeting_MeetingId(meetingId);

            return participants.stream()
                    .map(mp -> new MeetingParticipantDTO(
                            mp.getUser().getId(),
                            mp.getUser().getUserName(),
                            mp.getUser().getEmail(),
                            mp.getRole(),
                            mp.isMuted(),
                            mp.isVideoOn(),
                            mp.getJoinedAt()
                    ))
                    .collect(Collectors.toList());
        }

    }


