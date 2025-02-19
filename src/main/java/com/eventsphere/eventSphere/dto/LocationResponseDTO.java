package com.eventsphere.eventSphere.dto;

import com.eventsphere.eventSphere.entity.LocationType;
import com.eventsphere.eventSphere.entity.SessionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponseDTO {
    private Long id;
    private String name;
    private LocationType type;
    private SessionType sessionType;
    private String description;
    private Double latitude;
    private Double longitude;
    private String onlineUrl;
    private Long venueId; // Only ID to avoid full object reference
}
