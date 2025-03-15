package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.dto.LocationRequestDTO;
import com.eventsphere.eventSphere.dto.LocationResponseDTO;
import com.eventsphere.eventSphere.dto.VenueRequestDTO;
import com.eventsphere.eventSphere.dto.VenueResponseDTO;
import com.eventsphere.eventSphere.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

//    @Autowired
//    private  VenueService venueService;
    @Autowired
    private  LocationService locationService;

    // 🔑 Create a new Venue
//    @PostMapping("/venues")
//    public VenueResponseDTO createVenue(@RequestBody VenueRequestDTO request) {
//        return venueService.createVenue(request);
//    }

    // 🔑 Create a new Location
    @PostMapping("/locations")
    public LocationResponseDTO createLocation(@RequestBody LocationRequestDTO request) {
        return locationService.addLocation(request);
    }

    // 🔑 Update an existing Location
    @PutMapping("/locations/{locationId}")
    public LocationResponseDTO updateLocation(
            @PathVariable Long locationId,
            @RequestBody LocationRequestDTO request) {
        return locationService.updateLocation(locationId, request);
    }

    // 🔑 Delete a Location
    @DeleteMapping("/locations/{locationId}")
    public String deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
        return "Location deleted successfully.";
    }

}

