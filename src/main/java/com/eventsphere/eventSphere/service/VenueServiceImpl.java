//package com.eventsphere.eventSphere.service;
//
//import com.eventsphere.eventSphere.dto.VenueRequestDTO;
//import com.eventsphere.eventSphere.dto.VenueResponseDTO;
//import com.eventsphere.eventSphere.entity.Venue;
//import com.eventsphere.eventSphere.repository.VenueRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class VenueServiceImpl implements VenueService {
//
//    @Autowired
//    private VenueRepository venueRepository;
//
//    @Override
//    public List<VenueResponseDTO> getAllVenues() {
//        return venueRepository.findAll().stream()
//                .map(this::mapToVenueResponseDTO)
//                .toList();
//    }
//
//    @Override
//    public VenueResponseDTO createVenue(VenueRequestDTO request) {
//        Venue venue = Venue.builder().name(request.getName()).build();
//        venue = venueRepository.save(venue);
//        return mapToVenueResponseDTO(venue);
//    }
//
//    private VenueResponseDTO mapToVenueResponseDTO(Venue venue) {
//        return VenueResponseDTO.builder()
//                .id(venue.getId())
//                .name(venue.getName())
//                .build();  // No locations added for simplicity, add if needed
//    }
//
//    @Override
//    public VenueResponseDTO updateVenue(Long venueId, VenueRequestDTO request) {
//        Venue venue = venueRepository.findById(venueId)
//                .orElseThrow(() -> new IllegalArgumentException("Venue not found"));
//
//        venue.setName(request.getName());
//        Venue updatedVenue = venueRepository.save(venue);
//
//        return VenueResponseDTO.builder()
//                .id(updatedVenue.getId())
//                .name(updatedVenue.getName())
//                .build();
//    }
//
//    @Override
//    public void deleteVenue(Long venueId) {
//        venueRepository.deleteById(venueId);
//    }
//
//}
