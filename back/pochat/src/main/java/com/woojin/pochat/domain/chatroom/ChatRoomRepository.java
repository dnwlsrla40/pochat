package com.woojin.pochat.domain.chatroom;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findChatRoomByName(String name);

}
