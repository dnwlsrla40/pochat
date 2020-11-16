package com.woojin.pochat.service;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroom.ChatRoomRepository;
import com.woojin.pochat.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // index를 통해서 일정부분만 가져오는 것으로 변경 예정
    @Transactional
    public List<ChatRoom> getChatRoomList() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom create(ChatRoomDto.ChatRoomCreateRequestDto requestDto){
        return chatRoomRepository.save(requestDto.toEntity());
    }
}
