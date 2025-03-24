package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.User;
import com.eventsphere.eventSphere.service.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);

    void deleteByUserName(String username);

    @Query("SELECT u.id AS id, u.userName AS userName, u.email AS email FROM User u WHERE :role MEMBER OF u.roles")
    List<UserProjection> findByRole(@Param("role") String role);

    @Query("SELECT u.id FROM User u WHERE u.userName = :userName")
    Long getIdByUserName(@Param("userName") String username);
}
