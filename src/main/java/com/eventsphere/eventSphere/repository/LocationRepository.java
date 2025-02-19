package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByVenueId(Long venueId);
}
