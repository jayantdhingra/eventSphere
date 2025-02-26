package com.eventsphere.eventSphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private String cardNumber;
    private Long expMonth;
    private Long expYear;
    private String cvc;
}
