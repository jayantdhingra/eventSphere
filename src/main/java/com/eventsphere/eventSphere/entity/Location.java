package com.eventsphere.eventSphere.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String venue;
    private String address;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonBackReference
    private Conference conference;
}
