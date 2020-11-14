package com.woojin.pochat.controller;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.dto.ChatRoomDto;
import com.woojin.pochat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    
    // 채팅 리스트 화면(후에 자신이 만든 채팅창만 보이게 변경)
    
    // 채팅방 생성(친구중에서 초대하는 방식)
    public ChatRoom create(@RequestBody ChatRoomDto.ChatRoomCreateRequestDto requestDto){
        return chatRoomService.create(requestDto);
    }
    
    // 특정 채팅방 조회

    
}
