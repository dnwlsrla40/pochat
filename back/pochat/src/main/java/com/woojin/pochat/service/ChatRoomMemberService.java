package com.woojin.pochat.service;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroom.ChatRoomRepository;
import com.woojin.pochat.domain.chatroommember.ChatRoomMember;
import com.woojin.pochat.domain.chatroommember.ChatRoomMemberRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.ChatRoomMemberDto;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ChatRoomMemberService {
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    /*
        method
         - 채팅방 나가기
        parameter
         - id : chatroom id
     */
    @Transactional
    public void delete(Long id) {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(NoSuchElementException::new);

        // 채팅방에서 나가기
        chatRoomMemberRepository.deleteByUserAndChatRoom(loginUser,chatRoom);

        // 남아 있는 roomMember가 0명이면 채팅방 삭제
        if(chatRoomMemberRepository.countChatRoomMemberByChatRoom(chatRoom) == 0){
            System.out.println("crm Service + chatroom 삭제 crm Service + chatroom 삭제 crm Service + chatroom 삭제 crm Service + chatroom 삭제 crm Service + chatroom 삭제");
            CachingConnectionFactory cf = new CachingConnectionFactory("127.0.0.1", 5672);
            cf.setUsername("guest");
            cf.setPassword("guest");

            RabbitAdmin admin = new RabbitAdmin(cf);
            admin.deleteQueue(chatRoom.getName());
        }
    }

    @Transactional
    public void addMember(ChatRoomMemberDto.ChatRoomMemberCreateRequestDto requestDto) {

        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(requestDto.getId())).orElseThrow(NoSuchElementException::new);

        for (int i = 0; i < requestDto.getNewMember().size(); i++) {
            System.out.println(requestDto.getNewMember().get(i));
            User Member = userRepository.findByUsername(requestDto.getNewMember().get(i)).orElseThrow(NoSuchElementException::new);
            ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                    .user(Member)
                    .chatRoom(chatRoom)
                    .build();
            chatRoomMemberRepository.save(chatRoomMember);
        }
    }

    /*
        method
         - 채팅방에 있는 사람들 조회
        parameter
         - id : chatroom id
        return
         - chatroom에 있는 사람들
     */
    public List<User> getChatRoomMember(String id) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new);

        return chatRoomMemberRepository.findAllByChatRoom(chatRoom);
    }
}
