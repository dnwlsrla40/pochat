package com.woojin.pochat.domain.postlike;

import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, UUID> {
    @Query("select pl.post from PostLike pl where pl.post.chatRoom.id=?1 and pl.user = ?2")
    List<Post> findAllPostByChatRoomAndUser(Long id, User user);

    @Query("select pl from PostLike pl where pl.user = ?1 and pl.post=?2")
    PostLike findByUserAndPost(User loginUser, Post deletedPost);
}
