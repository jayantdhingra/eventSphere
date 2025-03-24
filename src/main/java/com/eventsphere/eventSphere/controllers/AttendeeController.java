package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.entity.Conference;
import com.eventsphere.eventSphere.entity.User;
import com.eventsphere.eventSphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attendee")
@RequiredArgsConstructor
public class AttendeeController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllAttendees() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@RequestParam Long id) {
        Optional<User> attendee = userService.findById(id);
    return attendee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getUserId")
    public Long getIdByUserName(@RequestParam String userName){
        return userService.getIdByUserName(userName);
    }
}
