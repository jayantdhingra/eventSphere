package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.dto.VenueRequestDTO;
import com.eventsphere.eventSphere.dto.VenueResponseDTO;

import java.util.List;

public interface VenueService {
    List<VenueResponseDTO> getAllVenues();
    VenueResponseDTO createVenue(VenueRequestDTO request);
    VenueResponseDTO updateVenue(Long venueId, VenueRequestDTO request);
    void deleteVenue(Long venueId);

}

