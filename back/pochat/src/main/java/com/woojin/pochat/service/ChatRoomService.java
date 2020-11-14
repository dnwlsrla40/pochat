package com.woojin.pochat.service;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroom.ChatRoomRepository;
import com.woojin.pochat.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom create(ChatRoomDto.ChatRoomCreateRequestDto requestDto){
        return chatRoomRepository.save(requestDto.toEntity());
    }
}
