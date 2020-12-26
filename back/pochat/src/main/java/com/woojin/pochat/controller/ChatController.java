package com.woojin.pochat.controller;

import com.woojin.pochat.domain.chat.Chat;
import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    /*
        method
         - 채팅 기록 가져오기
     */
    @GetMapping("/chat/history/{chatId}")
    public ResponseEntity<Map<String, Object>> getChatHistoryList(@PathVariable String chatId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<Chat> chatHistoryList= chatService.getChatHistoryList(chatId);
            resultMap.put("status", true);
            resultMap.put("data", chatHistoryList);
            // 요청 성공 + 새로운 리소스 생성
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
