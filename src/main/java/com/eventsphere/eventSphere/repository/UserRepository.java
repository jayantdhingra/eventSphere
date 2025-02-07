package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);

    void deleteByUserName(String username);
}
