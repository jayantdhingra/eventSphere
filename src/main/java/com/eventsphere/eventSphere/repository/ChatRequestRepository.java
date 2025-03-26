package com.eventsphere.eventSphere.repository;

import com.eventsphere.eventSphere.entity.ChatRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long> {

    Optional<ChatRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    Optional<ChatRequest> findBySenderIdAndReceiverIdAndStatus(Long senderId, Long receiverId, String status);

    Optional<ChatRequest> findByReceiverIdAndSenderIdAndStatus(Long receiverId, Long senderId, String status);

    List<ChatRequest> findByReceiverIdAndStatus(Long receiverId, String status);

    long countByReceiverIdAndStatus(Long receiverId, String status);

    @Query("SELECT c.status FROM ChatRequest c WHERE c.sender.id = :senderId AND c.receiver.id = :receiverId")
    String findUserConnections(@Param("senderId") Long senderId,@Param("receiverId") Long receiverId);


}
