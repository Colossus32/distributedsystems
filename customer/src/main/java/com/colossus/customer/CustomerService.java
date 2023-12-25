package com.colossus.customer;

import com.colossus.amqp.RabbitMQMessageProducer;
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
                              RabbitMQMessageProducer rabbitMQMessageProducer) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email is not taken
        customerRepository.saveAndFlush(customer);

        customer = customerRepository.getCustomerByEmail(request.email());

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) throw new IllegalStateException("fraudster");

        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(), customer.getEmail(), String.format("Hi %s, welcome to Colossus app", customer.getFirstName()));
        rabbitMQMessageProducer.publish("internal.exchange", "internal.notification.routing-key", notificationRequest);
    }
}
