package com.woojin.pochat.service;

import com.woojin.pochat.domain.WebSocketChatMessage;
import com.woojin.pochat.domain.chat.Chat;
import com.woojin.pochat.domain.chat.ChatRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Transactional
    public Chat createChat(WebSocketChatMessage webSocketChatMessage){

        User user = userRepository.findByUsername(webSocketChatMessage.getUsername()).orElseThrow(NoSuchElementException::new);

        Chat chat = Chat.builder()
                .chatRoom(webSocketChatMessage.getChannel())
                .sender(webSocketChatMessage.getUsername())
                .message(webSocketChatMessage.getMessage())
                .senderThumbnail(user.getThumbnail())
                .build();
        return chatRepository.save(chat);
    }

    @Transactional
    public List<Chat> getChatHistoryList(String chatId) {
        List<Chat> chatHistoryList = chatRepository.findAllByChatRoomId(chatId);
        return chatHistoryList;
    }
}
