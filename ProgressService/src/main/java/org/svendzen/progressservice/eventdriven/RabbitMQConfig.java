package org.svendzen.progressservice.eventdriven;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue progressQueue() {
        return new Queue("progressQueue", false);
    }
}
