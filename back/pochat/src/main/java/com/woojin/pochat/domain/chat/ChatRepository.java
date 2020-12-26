package com.woojin.pochat.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select c from Chat as c where c.chatRoom=?1 order by c.created_at")
    List<Chat> findAllByChatRoomId(String chatId);
}
