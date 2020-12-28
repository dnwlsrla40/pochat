package com.woojin.pochat.domain.chatroommember;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    @Query("select crm.chatRoom From ChatRoomMember as crm where crm.user=?1")
    List<ChatRoom> getChatRoomByUser(User user);

    @Modifying
    @Query("delete from ChatRoomMember as crm where crm.user=?1 and crm.chatRoom=?2")
    void deleteByUserAndChatRoom(User user, ChatRoom chatRoom);

    @Query("select count(crm) from ChatRoomMember as crm where crm.chatRoom = ?1")
    int countChatRoomMemberByChatRoom(ChatRoom chatRoom);

    @Query("select crm.user from ChatRoomMember as crm where crm.chatRoom = ?1 order by crm.user.username")
    List<User> findAllByChatRoom(ChatRoom chatRoom);


}
