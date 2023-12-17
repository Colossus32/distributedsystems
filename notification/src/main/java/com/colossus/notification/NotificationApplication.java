package com.colossus.notification;

import com.colossus.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.colossus.notification", "com.colossus.amqp"})
@EnableFeignClients(basePackages = "com.colossus.clients")
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    /*@Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer rabbitMQMessageProducer,
                                        NotificationConfig notificationConfig){
        return args ->{
            rabbitMQMessageProducer.publish(notificationConfig.getInternalExchange(),
                    notificationConfig.getInternalNotificationRoutingKey(),
                    "foo");
        };
    }*/
}
