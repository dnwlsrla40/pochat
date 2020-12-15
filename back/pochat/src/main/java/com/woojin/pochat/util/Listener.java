package com.woojin.pochat.util;

import com.woojin.pochat.domain.WebSocketChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @RabbitListener(queues = "coffee.queue")
    @SendTo("topic/javainuse")
    public WebSocketChatMessage processMessage(@Payload WebSocketChatMessage webSocketChatMessage){
        System.out.println("Listener: "+webSocketChatMessage);
        return webSocketChatMessage;
    }
}
