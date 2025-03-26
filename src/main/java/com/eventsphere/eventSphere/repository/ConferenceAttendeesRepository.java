package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.Conference;
import com.eventsphere.eventSphere.entity.ConferenceAttendees;
import com.eventsphere.eventSphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConferenceAttendeesRepository extends JpaRepository<ConferenceAttendees, Long> {
    List<ConferenceAttendees> findByConference(Conference conference);
    List<ConferenceAttendees> findByUser(User user);
    @Query("SELECT ca.conference FROM ConferenceAttendees ca WHERE ca.user = :user AND ca.paymentStatus='Completed'")
    List<Conference> findConferencesByUser(@Param("user") User user);

    boolean existsByUserAndConference(User user, Conference conference);
    Optional<ConferenceAttendees> findByUserAndConference(User user, Conference conference);

}