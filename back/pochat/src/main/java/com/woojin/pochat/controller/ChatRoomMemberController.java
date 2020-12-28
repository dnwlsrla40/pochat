package com.woojin.pochat.controller;

import com.woojin.pochat.domain.chatroommember.ChatRoomMember;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.dto.ChatRoomMemberDto;
import com.woojin.pochat.service.ChatRoomMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatRoomMemberController {

    private final ChatRoomMemberService chatRoomMemberService;

    /*
        method
         - 채팅방 나가기
        parameter
         - id : chatroom id
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/chatroommember/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            chatRoomMemberService.delete(id);
            resultMap.put("status", true);
            // 요청 성공 + 새로운 리소스 생성
            status = HttpStatus.CREATED;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
        method
         - 채팅방 멤버 추가
        parameter
         - id : chatroom id
         - roomMember : chatroom에 추가로 들어갈 사람들
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/chatroommember/add")
    public ResponseEntity<Map<String, Object>> addMember(@RequestBody ChatRoomMemberDto.ChatRoomMemberCreateRequestDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            chatRoomMemberService.addMember(requestDto);
            resultMap.put("status", true);
            // 요청 성공 + 새로운 리소스 생성
            status = HttpStatus.CREATED;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
        method
         - 채팅방에 있는 사람들 조회
        parameter
         - id : chatroom id
        return
         - chatroom에 있는 사람들
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/chatroommember/{id}")
    public ResponseEntity<Map<String, Object>> addMember(@PathVariable String id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<User> chatRoomMemberList = chatRoomMemberService.getChatRoomMember(id);
            resultMap.put("status", true);
            resultMap.put("data", chatRoomMemberList);
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
