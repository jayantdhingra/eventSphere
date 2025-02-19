package com.eventsphere.eventSphere.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "conferences")
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    private String name;
    private String description;
    private String date;

    @OneToMany(mappedBy="conference", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Speaker> speakers;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JsonManagedReference
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JsonManagedReference
    private List<Location> locations;


}
