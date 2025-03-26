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
import org.springframework.http.HttpStatus;
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

//    @GetMapping
//    public List<User> getAllAttendees() {
//        return userService.getAllAttendees();
//    }

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

    @GetMapping("/getRegisteredConferences")
    public List<Conference> getRegisteredConferences(@RequestParam String userName) {
        User u=userService.findByUserName(userName);
        return conferenceAttendeesRepository.findConferencesByUser(u);
    }

//    @PostMapping("/registerConference")
//    public ResponseEntity<Long> registerAttendee(@RequestBody Map<String, Object> request) {
//        Long conferenceId = Long.valueOf(request.get("conferenceId").toString());
//        Long userId = Long.valueOf(request.get("userId").toString());
//        String paymentStatus = "Completed";
//        // Validate conference and user existence
//        Conference conference = conferenceRepository.findById(conferenceId)
//                .orElseThrow(() -> new RuntimeException("Conference not found"));
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Create and save the attendee record
//        ConferenceAttendees attendee = new ConferenceAttendees();
//        attendee.setConference(conference);
//        attendee.setUser(user);
//        attendee.setRegisteredAt(LocalDateTime.now());
//        attendee.setPaymentStatus(paymentStatus);
//
//        ConferenceAttendees savedAttendee = conferenceAttendeesRepository.save(attendee);
//
//        return ResponseEntity.ok(savedAttendee.getId());
//    }

    @PostMapping("/registerConference")
    public ResponseEntity<Long> registerAttendee(@RequestBody Map<String, Object> request) {
        Long conferenceId = Long.valueOf(request.get("conferenceId").toString());
        Long userId = Long.valueOf(request.get("userId").toString());
        String paymentStatus = "Completed";

        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conference not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<ConferenceAttendees> existing = conferenceAttendeesRepository
                .findByUserAndConference(user, conference);

        ConferenceAttendees attendee;
        if (existing.isPresent()) {
            attendee = existing.get();
            attendee.setPaymentStatus(paymentStatus);
            attendee.setRegisteredAt(LocalDateTime.now());
        } else {
            attendee = new ConferenceAttendees();
            attendee.setUser(user);
            attendee.setConference(conference);
            attendee.setPaymentStatus(paymentStatus);
            attendee.setRegisteredAt(LocalDateTime.now());
        }

        ConferenceAttendees saved = conferenceAttendeesRepository.save(attendee);
        return ResponseEntity.ok(saved.getId());
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

    @GetMapping("/isRegistered")
    public ResponseEntity<Map<String, Object>> isUserRegistered(
            @RequestParam Long userId,
            @RequestParam Long conferenceId) {

        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Conference> conferenceOptional = conferenceRepository.findById(conferenceId);

        if (userOptional.isEmpty() || conferenceOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "User or Conference not found.",
                    "isRegistered", false
            ));
        }

        User user = userOptional.get();
        Conference conference = conferenceOptional.get();

        Optional<ConferenceAttendees> registration =
                conferenceAttendeesRepository.findByUserAndConference(user, conference);

        boolean isRegistered = registration.isPresent() &&
                "Completed".equalsIgnoreCase(registration.get().getPaymentStatus());

        return ResponseEntity.ok(Map.of(
                "isRegistered", isRegistered,
                "paymentStatus", registration.map(ConferenceAttendees::getPaymentStatus).orElse("None")
        ));
    }


    @PutMapping("/cancelRegistration")
    public ResponseEntity<String> cancelRegistration(
            @RequestParam Long conferenceId,
            @RequestParam Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Conference> conferenceOptional = conferenceRepository.findById(conferenceId);

        if (userOptional.isEmpty() || conferenceOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User or Conference not found.");
        }
        User user = userOptional.get();
        Conference conference = conferenceOptional.get();

        Optional<ConferenceAttendees> registration = conferenceAttendeesRepository
                .findByUserAndConference(user, conference);


        if (registration.isPresent()) {
            ConferenceAttendees attendee = registration.get();
            if(attendee.getPaymentStatus().equals("Cancelled")){
                return ResponseEntity.ok("Registration already cancelled.");
            }
            attendee.setPaymentStatus("Cancelled");
            conferenceAttendeesRepository.save(attendee);
            return ResponseEntity.ok("Registration cancelled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No registration found.");
        }
    }






}
