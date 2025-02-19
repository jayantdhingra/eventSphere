package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
}