package com.eventsphere.eventSphere.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = true)
    @JsonBackReference(value = "conference-locations")
    private Conference conference;

//    @ManyToOne
//    @JoinColumn(name = "venue_id", nullable = false)
//    @JsonIgnore // Prevent recursion issues
//    private Venue venue;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionType sessionType;

    private String description;
    private Double latitude;
    private Double longitude;
    private String onlineUrl;
}
