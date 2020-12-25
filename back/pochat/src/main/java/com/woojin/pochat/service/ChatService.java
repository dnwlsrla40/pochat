package com.woojin.pochat.service;

import com.woojin.pochat.domain.WebSocketChatMessage;
import com.woojin.pochat.domain.chat.Chat;
import com.woojin.pochat.domain.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public Chat createChat(WebSocketChatMessage webSocketChatMessage){
        Chat chat = Chat.builder()
                .chatRoom(webSocketChatMessage.getChannel())
                .sender(webSocketChatMessage.getUsername())
                .message(webSocketChatMessage.getMessage())
                .build();
        return chatRepository.save(chat);
    }
}
