package com.woojin.pochat.controller;

import com.woojin.pochat.domain.WebSocketChatMessage;
import com.woojin.pochat.service.ChatService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketChatController {

    private RabbitTemplate rabbitTemplate;
    private SimpMessagingTemplate template;

    private static final String EXCHANGE_NAME = "chat.topic";

    @Autowired
    private final ChatService chatService;

    public WebSocketChatController(RabbitTemplate rabbitTemplate, SimpMessagingTemplate template, ChatService chatService){
        this.rabbitTemplate = rabbitTemplate;
        this.template = template;
        this.chatService = chatService;
    }

    /*
        클라이언트 이벤트 발생 -> 컨트롤러에서 읽고 sub 한 Mq에 전달
        client(/app/chat.sendMessage) -> controller(/topic/javainuse) -> RabbitMq
     */
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/javainuse")
//    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage) {
//        return webSocketChatMessage;
//    }
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload WebSocketChatMessage webSocketChatMessage){
        System.out.println("websocketchatController" + webSocketChatMessage);
        chatService.createChat(webSocketChatMessage);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "chat.chatting." + webSocketChatMessage.getChannel(), webSocketChatMessage);
    }

    /*
        client(/app/chat.newUser) -> controller(/topic/javainuse) -> RabbitMq
     */
    @MessageMapping("/chat.newUser")
    public void newUser(@Payload WebSocketChatMessage webSocketChatMessage,
                                        SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("new User");
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getUsername());
        this.template.convertAndSend("/topic/"+webSocketChatMessage.getChannel(), webSocketChatMessage);
    }
}
