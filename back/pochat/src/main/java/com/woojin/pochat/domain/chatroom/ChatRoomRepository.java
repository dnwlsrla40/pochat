package com.woojin.pochat.domain.chatroom;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findAllChatRoomByName(String name);

    ChatRoom findChatRoomByName(String name);

    @Query(value = "select case when count(cr)> 0 then true else false end from ChatRoom cr where cr.name = ?1")
    Boolean existsByName(String name);
}
