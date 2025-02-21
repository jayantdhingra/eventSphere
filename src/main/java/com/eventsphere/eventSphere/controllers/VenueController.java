//package com.eventsphere.eventSphere.controllers;
//
//import com.eventsphere.eventSphere.dto.VenueResponseDTO;
//import com.eventsphere.eventSphere.dto.LocationResponseDTO;
//import com.eventsphere.eventSphere.service.LocationService;
//import com.eventsphere.eventSphere.service.VenueService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/venues")
//@RequiredArgsConstructor
//public class VenueController {
//    @Autowired
//    private  VenueService venueService;
//
//    @Autowired
//    private LocationService locationService;
//
////    @GetMapping
////    public List<VenueResponseDTO> getAllVenues() {
////        return venueService.getAllVenues();
////    }
//
//    @GetMapping("/{venueId}/locations")
//    public List<LocationResponseDTO> getLocationsByVenue(@PathVariable Long venueId) {
//        return locationService.getLocationsByVenue(venueId);
//    }
//}

