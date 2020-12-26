package com.woojin.pochat.controller;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.friend.Friend;
import com.woojin.pochat.dto.ChatRoomDto;
import com.woojin.pochat.dto.FriendDto;
import com.woojin.pochat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅 리스트 화면(후에 자신이 만든 채팅창만 보이게 변경)
    @GetMapping("/chatroom/list")
    public ResponseEntity<Map<String, Object>> getCharRoomList(HttpSession session){
        System.out.println(session.getId());
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<ChatRoom> chatRoomList= chatRoomService.getChatRoomList();
            resultMap.put("status", true);
            resultMap.put("data", chatRoomList);
            // 요청 성공 + 새로운 리소스 생성
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
        method
         - 채팅방 생성(친구중에서 초대하는 방식)
        parameter
         - name : chatroom 이름
         - roomMember : chatroom에 들어가 있는 사람들
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/chatroom")
    public ResponseEntity<Map<String, Object>> create(@RequestBody ChatRoomDto.ChatRoomCreateRequestDto requestDto) {
        System.out.println(requestDto.getRoomMember());
        System.out.println("chatroomDto: " + requestDto);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            ChatRoom createdRoom = chatRoomService.create(requestDto);
            resultMap.put("status", true);
            resultMap.put("data", createdRoom);
            // 요청 성공 + 새로운 리소스 생성
            status = HttpStatus.CREATED;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
