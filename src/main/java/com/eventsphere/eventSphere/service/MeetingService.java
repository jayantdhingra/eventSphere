package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.entity.Meeting;
import com.eventsphere.eventSphere.entity.User;
import com.eventsphere.eventSphere.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    public String createMeeting(User host) {
        Meeting meeting = new Meeting();
        meeting.setHost(host); // ✅ Correctly set User object
        meeting.setMeetingId(UUID.randomUUID().toString());
        meeting.setStartTime(LocalDateTime.now());
        meeting.setStatus("Yet to start");
        Meeting m=meetingRepository.save(meeting);
        return m.getMeetingId();
    }

}
