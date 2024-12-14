package org.svendzen.contentservice.eventdriven;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue exerciseCompletedQueue() {
        return new Queue("exerciseCompletedQueue", true);
    }

    @Bean
    public DirectExchange exerciseExchange() {
        return new DirectExchange("exerciseExchange", true, false);
    }

    @Bean
    public Binding binding(Queue exerciseCompletedQueue, DirectExchange exerciseExchange) {
        return BindingBuilder.bind(exerciseCompletedQueue).to(exerciseExchange).with("exercise.completed");
    }
}
