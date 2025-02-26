package com.eventsphere.eventSphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO {
    private Long id;
    private Double price;
    private boolean paymentStatus;
    private LocalDateTime purchaseDate;
}
