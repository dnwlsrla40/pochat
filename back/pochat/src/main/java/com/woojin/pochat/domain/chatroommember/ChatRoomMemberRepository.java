package com.woojin.pochat.domain.chatroommember;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    @Query("select crm.chatRoom From ChatRoomMember as crm where crm.user=?1")
    List<ChatRoom> getChatRoomByUser(User user);
}
