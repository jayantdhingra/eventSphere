package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}