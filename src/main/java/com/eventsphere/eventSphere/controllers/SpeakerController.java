package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.entity.Speaker;
import com.eventsphere.eventSphere.repository.ConferenceRepository;
import com.eventsphere.eventSphere.repository.SpeakerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @GetMapping
    public List<Speaker> getAllSpeakers() {
        return speakerRepository.findAll();
    }

    @PostMapping("/{conferenceId}")
    public ResponseEntity<Speaker> addSpeakerToConference(@PathVariable Long conferenceId, @RequestBody Speaker speaker) {
        return conferenceRepository.findById(conferenceId).map(conference -> {
            speaker.setConference(conference);
            return ResponseEntity.ok(speakerRepository.save(speaker));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSpeaker(@PathVariable Long id) {
        return speakerRepository.findById(id).map(speaker -> {
            speakerRepository.delete(speaker);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
