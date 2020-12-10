package com.woojin.pochat.service;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroom.ChatRoomRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    // index를 통해서 일정부분만 가져오는 것으로 변경 예정
    @Transactional
    public List<ChatRoom> getChatRoomList() {
        return chatRoomRepository.findAll();
    }

    @Transactional
    public ChatRoom create(ChatRoomDto.ChatRoomCreateRequestDto requestDto){
        List<User> roomMemberList = new ArrayList<>();
        for (int i = 0; i < requestDto.getRoomMember().size(); i++) {
            System.out.println(requestDto.getRoomMember().get(i));
            User roomMember = userRepository.findByUsername(requestDto.getRoomMember().get(i)).orElseThrow(NoSuchElementException::new);
            roomMemberList.add(roomMember);
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .name(requestDto.getName())
                .roomMember(roomMemberList)
                .build();
        return chatRoomRepository.save(chatRoom);
    }
}
