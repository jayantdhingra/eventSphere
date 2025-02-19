package com.eventsphere.eventSphere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueResponseDTO {
    private Long id;
    private String name;
    private List<LocationResponseDTO> locations;
}
