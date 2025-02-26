package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.dto.PaymentDTO;
import com.eventsphere.eventSphere.entity.Payment;
import com.eventsphere.eventSphere.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/pay")
    public ResponseEntity<String> processPaymentMethodID(@RequestBody PaymentDTO paymentDTO) throws StripeException {
        String paymentMethodID = paymentService.processPaymentMethodIDImpl(paymentDTO);
        return ResponseEntity.ok("Payment successful. Transaction ID: " + paymentMethodID);
    }

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@RequestParam Long userId,
                                                 @RequestParam Long ticketId,
                                                 @RequestBody String paymentMethodID) throws StripeException {
        Payment payment = paymentService.processPayment(userId, ticketId, paymentMethodID);
        return ResponseEntity.ok("Payment successful. Transaction ID: " + payment.getTransactionId());
    }

}
