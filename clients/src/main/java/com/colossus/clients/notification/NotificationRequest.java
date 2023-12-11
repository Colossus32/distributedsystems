package com.colossus.clients.notification;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {
    private int toCustomerId;
    private String toCustomerEmail;
    private String message;
}
