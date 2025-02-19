package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.entity.Conference;
import com.eventsphere.eventSphere.repository.ConferenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @GetMapping
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conference> getConferenceById(@PathVariable Long id) {
        Optional<Conference> conference = conferenceRepository.findById(id);
        return conference.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Conference createConference(@RequestBody Conference conference) {
        return conferenceRepository.save(conference);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conference> updateConference(@PathVariable Long id, @RequestBody Conference updatedConference) {
        return conferenceRepository.findById(id).map(conference -> {
            conference.setName(updatedConference.getName());
            conference.setDescription(updatedConference.getDescription());
            conference.setDate(updatedConference.getDate());
            return ResponseEntity.ok(conferenceRepository.save(conference));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteConference(@PathVariable Long id) {
        return conferenceRepository.findById(id).map(conference -> {
            conferenceRepository.delete(conference);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
