package com.woojin.pochat.util;

import com.woojin.pochat.domain.WebSocketChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    private SimpMessagingTemplate template;

    @Autowired
    public Listener(SimpMessagingTemplate template){
        this.template = template;
    }

    @RabbitListener(queues = "coffee.queue")
    // @SendTo("topic/javainuse")
    public void processMessage(@Payload WebSocketChatMessage webSocketChatMessage){
        System.out.println("Listener: "+webSocketChatMessage);
        this.template.convertAndSend("/topic/javainuse", webSocketChatMessage);
        // return webSocketChatMessage;
    }
}
