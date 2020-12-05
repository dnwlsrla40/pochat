package com.woojin.pochat.domain.friend;

import com.woojin.pochat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("select f from Friend as f where f.sender=?1")
    List<Friend> findAllBySenderName(User sender);

    @Query("select f from Friend as f where f.sender=?1 and f.isAccept=true")
    List<Friend> findAllbySenderNameAndIsAccept(User sender);

    @Query("select f from Friend as f where f.sender.username=?1 and f.recipient.username=?2")
    Friend findAllBySenderNameAndRecipientName(String senderName, String recipientName);

    @Query("select f from Friend as f where f.recipient=?1 and f.isAccept=false")
    List<Friend> findAllbyRecipientAndIsAccept(User loginUser);

    @Query("select f from Friend as f where (f.recipient=?1 or f.sender=?1) and f.isAccept=true")
    List<Friend> findAllbyNameAndIsAccept(User loginUser);
}
