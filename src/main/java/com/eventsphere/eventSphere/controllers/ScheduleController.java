package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.entity.Schedule;
import com.eventsphere.eventSphere.repository.ConferenceRepository;
import com.eventsphere.eventSphere.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @PostMapping("/{conferenceId}")
    public ResponseEntity<Schedule> addScheduleToConference(@PathVariable Long conferenceId, @RequestBody Schedule schedule) {
        return conferenceRepository.findById(conferenceId).map(conference -> {
            schedule.setConference(conference);
            return ResponseEntity.ok(scheduleRepository.save(schedule));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable Long id) {
        return scheduleRepository.findById(id).map(schedule -> {
            scheduleRepository.delete(schedule);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
