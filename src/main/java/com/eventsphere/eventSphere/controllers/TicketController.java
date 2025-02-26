package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.dto.TicketRequestDTO;
import com.eventsphere.eventSphere.entity.Ticket;
import com.eventsphere.eventSphere.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/register")
    public ResponseEntity<Ticket> registerForConference(@RequestParam Long userId,
                                                        @RequestParam Long conferenceId,
                                                        @RequestBody TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = ticketService.registerUserForConference(userId, conferenceId, ticketRequestDTO);
        return ResponseEntity.ok(ticket);
    }
}
