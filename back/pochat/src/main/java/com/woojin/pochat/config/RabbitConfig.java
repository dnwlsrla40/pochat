package com.woojin.pochat.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private static final String EXCHANGE_NAME = "cafe.topic";
    private static final String QUEUE_NAME = "coffee.queue";
    private static final String ROUTING_KEY = "order.coffee.#";

    // private ConnectionFactory connectionFactory;

    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // this.connectionFactory = connectionFactory;
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

//    @Bean
//    RabbitAdmin rabbitAdmin() {
//        return new RabbitAdmin(this.connectionFactory);
//    }
}
