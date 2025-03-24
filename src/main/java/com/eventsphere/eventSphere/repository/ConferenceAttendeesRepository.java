package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.Conference;
import com.eventsphere.eventSphere.entity.ConferenceAttendees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConferenceAttendeesRepository extends JpaRepository<ConferenceAttendees, Long> {
    List<ConferenceAttendees> findByConference(Conference conference);
}