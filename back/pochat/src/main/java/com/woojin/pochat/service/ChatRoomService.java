package com.woojin.pochat.service;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroom.ChatRoomRepository;
import com.woojin.pochat.domain.chatroommember.ChatRoomMember;
import com.woojin.pochat.domain.chatroommember.ChatRoomMemberRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.ChatRoomDto;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;


    @Transactional
    public List<ChatRoom> getChatRoomList() {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        return chatRoomMemberRepository.getChatRoomByUser(loginUser);
    }

    @Transactional
    public String create(ChatRoomDto.ChatRoomCreateRequestDto requestDto){
        for (int i = 0; i < requestDto.getRoomMember().size(); i++) {
            System.out.println(requestDto.getRoomMember().get(i));
            User roomMember = userRepository.findByUsername(requestDto.getRoomMember().get(i)).orElseThrow(NoSuchElementException::new);
            ChatRoom chatRoom = ChatRoom.builder()
                    .name(requestDto.getName())
                    .build();
            ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                    .user(roomMember)
                    .chatRoom(chatRoom)
                    .build();
            chatRoomMemberRepository.save(chatRoomMember);
        }
        return "success";
    }
}
