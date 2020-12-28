package com.woojin.pochat.domain.post;

import com.woojin.pochat.domain.BaseTimeEntity;
import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(length = 150, name = "short_description")
    private String shortDescription;

    @Column(name = "is_favorite", columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Boolean isFavorite;

    @Column(name = "is_private", columnDefinition = "TINYINT")
    @ColumnDefault("1")
    private Boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "writer", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_room", nullable = false)
    private ChatRoom chatRoom;

    @Builder
    public Post(String title, String body, String shortDescription, Boolean isPrivate, User user, ChatRoom chatRoom) {
        Assert.notNull(title, "title must be not null");
        Assert.notNull(body, "body must be not null");
        Assert.notNull(shortDescription, "shortDescription must be not null");
        Assert.notNull(isPrivate, "isPrivate must be not null");
        Assert.notNull(user, "user must be not null");
        Assert.notNull(chatRoom, "chatRoom must be not null");

        this.title = title;
        this.body = body;
        this.shortDescription = shortDescription;
        this.isPrivate = isPrivate;
        this.user = user;
        this.chatRoom = chatRoom;
    }

    // null 값이 들어올 경우 초기화
    @PrePersist
    public void prePersist(){
        this.isPrivate = this.isPrivate == null ? false : this.isPrivate;
        this.shortDescription =  isNull(this.shortDescription) ? (this.body.length() > 70 ? this.body.substring(0,70) : this.body) : this.shortDescription;
        this.isFavorite = this.isFavorite == null ? false : this.isFavorite;
    }

    public void update(String title, String shortDescription, String body, Boolean isPrivate){
        this.title = title;
        this.shortDescription = shortDescription;
        this.body = body;
        this.isPrivate = isPrivate;
    }

    public Boolean isNull(String shortDescription){

        return shortDescription.equals("") || shortDescription == null;
    }
}
