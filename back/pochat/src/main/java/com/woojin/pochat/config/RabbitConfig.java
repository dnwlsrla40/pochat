package com.woojin.pochat.config;

import com.woojin.pochat.util.Listener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {
    private static final String EXCHANGE_NAME = "chat.topic";
//    private static final String QUEUE_NAME = "chat.queue2";
    private static final String ROUTING_KEY = "chat.chatting.#";

    // private ConnectionFactory connectionFactory;

//    @Bean
//    Queue queue(){
//        return new Queue(QUEUE_NAME, false);
//    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//    }

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

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
//        container.setMessageListener(listenerAdapter);
        return container;
    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(Listener listener){
//        return new MessageListenerAdapter(listener);
//    }

//    @Bean
//    RabbitAdmin rabbitAdmin() {
//        return new RabbitAdmin(this.connectionFactory);
//    }
}
