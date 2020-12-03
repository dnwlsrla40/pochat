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
}
