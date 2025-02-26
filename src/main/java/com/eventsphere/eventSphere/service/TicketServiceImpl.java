package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.dto.TicketRequestDTO;
import com.eventsphere.eventSphere.entity.Conference;
import com.eventsphere.eventSphere.entity.Ticket;
import com.eventsphere.eventSphere.entity.User;
import com.eventsphere.eventSphere.repository.ConferenceRepository;
import com.eventsphere.eventSphere.repository.TicketRepository;
import com.eventsphere.eventSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Override
    public Ticket registerUserForConference(Long userId, Long conferenceId, TicketRequestDTO ticketRequestDTO) {
        // Validate user existence
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate conference existence
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conference not found"));

        // Create a new ticket
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setConference(conference);
        ticket.setPrice(ticketRequestDTO.getPrice());
        ticket.setPaymentStatus(false);
        //ticket.setTicketType(ticketType);
        ticket.setPurchaseDate(LocalDateTime.now());

        // Save the ticket in the database
        return ticketRepository.save(ticket);
    }
}
