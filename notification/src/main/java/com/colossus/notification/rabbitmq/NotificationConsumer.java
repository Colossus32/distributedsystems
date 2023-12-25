package com.colossus.notification.rabbitmq;

import com.colossus.clients.notification.NotificationRequest;
import com.colossus.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public record NotificationConsumer(NotificationService notificationService) {

    @RabbitListener(queues = "notification.queue")
    public void consume(NotificationRequest notificationRequest) {
        log.info("Consumed {} from queue", notificationRequest);
        notificationService.send(notificationRequest);
    }
}
