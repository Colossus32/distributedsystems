package com.colossus.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {
}
