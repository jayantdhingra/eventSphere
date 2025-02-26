package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.dto.TicketRequestDTO;
import com.eventsphere.eventSphere.entity.Ticket;

public interface TicketService {
    Ticket registerUserForConference(Long userId, Long conferenceId, TicketRequestDTO ticketRequestDTO);
}
