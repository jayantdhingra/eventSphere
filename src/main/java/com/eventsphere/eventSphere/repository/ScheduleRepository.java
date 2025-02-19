package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}

