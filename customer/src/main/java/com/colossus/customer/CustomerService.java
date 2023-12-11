package com.colossus.customer;

import com.colossus.clients.fraud.FraudCheckResponse;
import com.colossus.clients.fraud.FraudClient;
import com.colossus.clients.notification.NotificationClient;
import com.colossus.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              RestTemplate restTemplate,
                              FraudClient fraudClient,
                              NotificationClient notificationClient) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email is not taken
        customerRepository.saveAndFlush(customer);
        // todo: check if fraudster

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) throw new IllegalStateException("fraudster");

        // todo: make async. i.e add to queue
        notificationClient.sendNotification(
                new NotificationRequest(customer.getId(), customer.getEmail(), String.format("Hi %s, welcome to Colossus app", customer.getFirstName()))
        );
    }
}
