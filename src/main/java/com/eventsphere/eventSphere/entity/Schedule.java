package com.eventsphere.eventSphere.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedules")
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionName;
    private String startTime;
    private String endTime;


    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonBackReference
    private Conference conference;

}
