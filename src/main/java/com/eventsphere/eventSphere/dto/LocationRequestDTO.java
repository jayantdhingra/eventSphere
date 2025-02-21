package com.eventsphere.eventSphere.dto;

import com.eventsphere.eventSphere.entity.LocationType;
import com.eventsphere.eventSphere.entity.SessionType;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestDTO {

//    @NotEmpty
//    private Long venueId;

    @NotEmpty
    private String name;

   @NotEmpty
    private LocationType type;

    @NotEmpty
    private SessionType sessionType;

    private String description;
    private Double latitude;
    private Double longitude;
    private String onlineUrl;
}
