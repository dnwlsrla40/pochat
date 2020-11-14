package com.woojin.pochat.controller;

import com.woojin.pochat.domain.WebSocketChatMessage;
import com.woojin.pochat.domain.chat.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    @MessageMapping("/chat/message")
    @SendTo("/topic/javainuse")
    public Chat sendMessage(@Payload Chat chat) {
        if(chat.getType().equals("ENTER"))
            chat.setMessage(chat.getSender() + "님이 입장하셨습니다.");
        return chat;
    }
}
