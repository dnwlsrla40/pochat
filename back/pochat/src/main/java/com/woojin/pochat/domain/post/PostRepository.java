package com.woojin.pochat.domain.post;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post as p where p.user.username = ?1")
    List<Post> findAllByUsername(String username);

    @Query("select p from Post as p where p.user.username = ?1 and p.favorite = true")
    List<Post> findAllFavoritePostByUsername(String username);
}
