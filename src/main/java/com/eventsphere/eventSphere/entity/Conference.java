package com.eventsphere.eventSphere.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "conferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conference_id; //changed column name
    private String name;
    private String description;
    private String date;

//    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference(value = "conference-speakers")  // Reference for speakers
//    private List<Speaker> speakers;

    @ManyToMany
    @JoinTable(
            name = "conference_speakers",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> speakers;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "conference-schedules")  // Reference for schedules
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "conference-locations")  // Reference for locations
    private List<Location> locations;

    @Override
    public String toString() {
        return "Conference{" +
                "conference_id=" + conference_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}

