package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);

    void deleteByUserName(String username);

    @Query("SELECT u.id FROM User u WHERE u.userName = :userName")
    Long getIdByUserName(@Param("userName") String username);
}
