package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.dto.PaymentDTO;
import com.eventsphere.eventSphere.entity.Payment;
import com.eventsphere.eventSphere.entity.Ticket;
import com.eventsphere.eventSphere.entity.User;
import com.eventsphere.eventSphere.repository.PaymentRepository;
import com.eventsphere.eventSphere.repository.TicketRepository;
import com.eventsphere.eventSphere.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;

import java.util.Optional;

@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "password123";

    public void duplicateCodeExample() {
        try{
            String message1 = "This is duplicate code";
            System.out.println(message1);

            String message2 = "This is duplicate code";
            System.out.println(message2);

        }catch(Exception ex){

        }
    }

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(UserRepository userRepository, TicketRepository ticketRepository, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.paymentRepository = paymentRepository;
    }

    public String processPaymentMethodIDImpl(PaymentDTO paymentDTO) throws StripeException {

        // Initialize Stripe API key
        Stripe.apiKey = stripeSecretKey;



//        PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
//                .setType(PaymentMethodCreateParams.Type.CARD)
//                .setCard(
//                        PaymentMethodCreateParams.CardDetails.builder()
//                                .setNumber(paymentDTO.getCardNumber())
//                                .setExpMonth(paymentDTO.getExpMonth())
//                                .setExpYear(paymentDTO.getExpYear())
//                                .setCvc(paymentDTO.getCvc())
//                                .build()
//                ).build();

        TokenCreateParams params = TokenCreateParams.builder()
                .setCard(
                        TokenCreateParams.Card.builder()
                                .setNumber(paymentDTO.getCardNumber()) // Test Visa card number
                                .setExpMonth(String.valueOf(paymentDTO.getExpMonth())) // Expiration month
                                .setExpYear(String.valueOf(paymentDTO.getExpYear())) // Expiration year
                                .setCvc(paymentDTO.getCvc()) // CVC
                                .build()
                ).build();

        Token token = Token.create(params);

//        PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);
//        String paymentMethodId = paymentMethod.getId();

        return token.getId();

    }

    @Transactional
    public Payment processPayment(Long userId, Long ticketId, String paymentMethodID) throws StripeException {
        // Validate User
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        // Validate Ticket
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isEmpty()) {
            throw new RuntimeException("Ticket not found!");
        }

        Ticket ticket = ticketOptional.get();

        // Initialize Stripe API key
        Stripe.apiKey = stripeSecretKey;

//        // Create paymentMethodID
//
//        //Setting card details
//        PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
//                .setType(PaymentMethodCreateParams.Type.CARD)
//                .setCard(
//                        PaymentMethodCreateParams.CardDetails.builder()
//                                .setNumber(paymentDTO.getCardNumber())
//                                .setExpMonth(paymentDTO.getExpMonth())
//                                .setExpYear(paymentDTO.getExpYear())
//                                .setCvc(paymentDTO.getCvc())
//                                .build()
//                ).build();
//
//        PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);
//        String paymentMethodId = paymentMethod.getId();

        // Create Payment Intent
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (ticket.getPrice() * 100)) // Convert amount to cents
                .setCurrency("usd")
                .setPaymentMethod(paymentMethodID)
                .setReturnUrl("https://google.com")
                .setConfirm(true) // Automatically confirm the payment
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Save Payment Details
        Payment payment = new Payment();
        payment.setUser(userOptional.get());
        payment.setTicket(ticket);
        payment.setAmount(ticket.getPrice());
//        payment.setStatus(paymentIntent.getStatus());
        payment.setTransactionId(paymentIntent.getId());

        return paymentRepository.save(payment);
    }
}
