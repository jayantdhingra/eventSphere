package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    boolean existsByMeetingId(String meetingId);
}
