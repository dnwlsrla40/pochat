package com.woojin.pochat.domain.post;


import com.woojin.pochat.domain.chatroom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post as p where p.chatRoom.id = ?1")
    List<Post> findAllByChatRoom(Long chatId);

}
