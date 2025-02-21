package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.dto.LocationRequestDTO;
import com.eventsphere.eventSphere.dto.LocationResponseDTO;
import java.util.List;

public interface LocationService {
//    List<LocationResponseDTO> getLocationsByVenue(Long venueId);                 // GET locations for a venue
    LocationResponseDTO addLocation(LocationRequestDTO request);                 // POST add a location
    LocationResponseDTO updateLocation(Long locationId, LocationRequestDTO request);  // PUT update location
    void deleteLocation(Long locationId);                                        // DELETE location
}
