package com.woojin.pochat.controller;

import com.woojin.pochat.domain.WebSocketChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    /*
        클라이언트 이벤트 발생 -> 컨트롤러에서 읽고 sub 한 Mq에 전달
        client(/app/chat.sendMessage) -> controller(/topic/javainuse) -> RabbitMq
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/javainuse")
    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage) {
        return webSocketChatMessage;
    }

    /*
        client(/app/chat.newUser) -> controller(/topic/javainuse) -> RabbitMq
     */
    @MessageMapping("/chat.newUser")
    @SendTo("/topic/javainuse")
    public WebSocketChatMessage newUser(@Payload WebSocketChatMessage webSocketChatMessage,
                                        SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("new User");
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getUsername());
        return webSocketChatMessage;
    }
}
