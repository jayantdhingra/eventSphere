package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.dto.LocationRequestDTO;
import com.eventsphere.eventSphere.dto.LocationResponseDTO;
import com.eventsphere.eventSphere.entity.Location;
import com.eventsphere.eventSphere.entity.Venue;
import com.eventsphere.eventSphere.repository.LocationRepository;
import com.eventsphere.eventSphere.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Override
    public List<LocationResponseDTO> getLocationsByVenue(Long venueId) {
        return locationRepository.findByVenueId(venueId).stream()
                .map(this::mapToLocationResponseDTO)
                .toList();
    }

    @Override
    public LocationResponseDTO addLocation(LocationRequestDTO request) {
        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new IllegalArgumentException("Venue not found"));

        Location location = Location.builder()
                .venue(venue)
                .name(request.getName())
                .type(request.getType())
                .sessionType(request.getSessionType())
                .description(request.getDescription())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .onlineUrl(request.getOnlineUrl())
                .build();

        Location savedLocation = locationRepository.save(location);
        return mapToLocationResponseDTO(savedLocation);
    }

    @Override
    public LocationResponseDTO updateLocation(Long locationId, LocationRequestDTO request) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));

        location.setName(request.getName());
        location.setType(request.getType());
        location.setSessionType(request.getSessionType());
        location.setDescription(request.getDescription());
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setOnlineUrl(request.getOnlineUrl());

        Location updatedLocation = locationRepository.save(location);
        return mapToLocationResponseDTO(updatedLocation);
    }

    @Override
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }

    private LocationResponseDTO mapToLocationResponseDTO(Location location) {
        return LocationResponseDTO.builder()
                .id(location.getId())
                .name(location.getName())
                .type(location.getType())
                .sessionType(location.getSessionType())
                .description(location.getDescription())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .onlineUrl(location.getOnlineUrl())
                .venueId(location.getVenue().getId())
                .build();
    }
}
