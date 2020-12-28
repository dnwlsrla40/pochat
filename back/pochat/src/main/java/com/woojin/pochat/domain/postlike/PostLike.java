package com.woojin.pochat.domain.postlike;

import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @Builder
    public PostLike(User user, Post post) {

        Assert.notNull(user,"user must not be empty.");
        Assert.notNull(post,"post must not be empty.");

        this.user = user;
        this.post = post;
    }
}
