package com.woojin.pochat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final String EXCHANGE_NAME = "chat.topic";

    @Autowired
    private final ChatService chatService;

    public WebSocketChatController(RabbitTemplate rabbitTemplate, ChatService chatService){
        this.rabbitTemplate = rabbitTemplate;
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
        ObjectMapper objectMapper = new ObjectMapper();
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "chat.chatting." + webSocketChatMessage.getChannel(), webSocketChatMessage);

//        try {
//            String json = objectMapper.writeValueAsString(webSocketChatMessage);
//            byte[] bytes = json.getBytes("UTF-8");
//        }
//        catch (Exception e) {
//            System.out.println("흑흑");
//        }
    }

    /*
        client(/app/chat.newUser) -> controller(/topic/javainuse) -> RabbitMq
     */
    @MessageMapping("/chat.newUser")
    public void newUser(@Payload WebSocketChatMessage webSocketChatMessage) {
        System.out.println("new User");
//        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getUsername());
//        this.template.convertAndSend("/topic/"+webSocketChatMessage.getChannel(), webSocketChatMessage);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "chat.chatting." + webSocketChatMessage.getChannel(), webSocketChatMessage);
    }
}
