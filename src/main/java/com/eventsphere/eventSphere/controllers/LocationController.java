package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.entity.Location;
import com.eventsphere.eventSphere.repository.ConferenceRepository;
import com.eventsphere.eventSphere.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @PostMapping("/{conferenceId}")
    public ResponseEntity<Location> addLocationToConference(@PathVariable Long conferenceId, @RequestBody Location location) {
        return conferenceRepository.findById(conferenceId).map(conference -> {
            location.setConference(conference);
            return ResponseEntity.ok(locationRepository.save(location));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLocation(@PathVariable Long id) {
        return locationRepository.findById(id).map(location -> {
            locationRepository.delete(location);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
