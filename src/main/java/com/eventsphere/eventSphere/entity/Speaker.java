package com.eventsphere.eventSphere.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "speakers")
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String topic;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonBackReference
    private Conference conference;

}
