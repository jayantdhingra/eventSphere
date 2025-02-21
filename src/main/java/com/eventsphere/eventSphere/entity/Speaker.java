package com.eventsphere.eventSphere.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "speakers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String topic;

    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = false)
    @JsonBackReference(value = "conference-speakers")  // Must match the @JsonManagedReference in Conference
    private Conference conference;
}
