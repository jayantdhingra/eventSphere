package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.entity.Conference;
import com.eventsphere.eventSphere.entity.ConferenceAttendees;
import com.eventsphere.eventSphere.entity.User;
import com.eventsphere.eventSphere.repository.ConferenceAttendeesRepository;
import com.eventsphere.eventSphere.repository.ConferenceRepository;
import com.eventsphere.eventSphere.repository.UserRepository;
import com.eventsphere.eventSphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/attendee")
@RequiredArgsConstructor
public class AttendeeController {

    @Autowired
    UserService userService;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ConferenceAttendeesRepository conferenceAttendeesRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> getAllAttendees() {
        return userService.getAllAttendees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@RequestParam Long id) {
        Optional<User> attendee = userService.findById(id);
    return attendee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getIdByUserName")
    public Long getIdByUserName(@RequestParam String userName){
        return userService.getIdByUserName(userName);
    }

    @GetMapping("/getAllConferences")
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }


    @PostMapping("/registerConference")
    public ResponseEntity<Long> registerAttendee(@RequestBody Map<String, Object> request) {
        Long conferenceId = Long.valueOf(request.get("conferenceId").toString());
        Long userId = Long.valueOf(request.get("userId").toString());
        String paymentStatus = request.get("paymentStatus").toString();
        // Validate conference and user existence
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conference not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and save the attendee record
        ConferenceAttendees attendee = new ConferenceAttendees();
        attendee.setConference(conference);
        attendee.setUser(user);
        attendee.setRegisteredAt(LocalDateTime.now());
        attendee.setPaymentStatus(paymentStatus);

        ConferenceAttendees savedAttendee = conferenceAttendeesRepository.save(attendee);

        return ResponseEntity.ok(savedAttendee.getId());
    }

    @GetMapping("/conferenceAttendees/{conferenceId}")
    public ResponseEntity<List<User>> getUsersByConference(@PathVariable Long conferenceId) {
        // Validate if the conference exists
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conference not found"));

        // Fetch all attendees for the given conference
        List<ConferenceAttendees> attendees = conferenceAttendeesRepository.findByConference(conference);

        // Extract user IDs from attendees
        List<Long> userIds = attendees.stream().map(attendee -> attendee.getUser().getId()).toList();

        // Fetch users using user IDs
        List<User> users = userRepository.findAllById(userIds);

        return ResponseEntity.ok(users);
    }




}
