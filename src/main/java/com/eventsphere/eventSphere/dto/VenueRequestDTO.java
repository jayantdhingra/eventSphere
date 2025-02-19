package com.eventsphere.eventSphere.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueRequestDTO {
    @NotEmpty
    private String name;
}
