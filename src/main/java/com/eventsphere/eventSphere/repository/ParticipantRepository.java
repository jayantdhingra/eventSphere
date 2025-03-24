package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.MeetingParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<MeetingParticipants, Long> {

    List<MeetingParticipants> findByMeeting_MeetingId(String meetingId);
}
