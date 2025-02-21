package com.eventsphere.eventSphere.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedules")
@Data
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
    @JsonBackReference(value = "conference-schedules") // Corrected reference
    private Conference conference;
}
