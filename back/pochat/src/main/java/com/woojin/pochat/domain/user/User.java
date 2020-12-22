package com.woojin.pochat.domain.user;

import com.woojin.pochat.domain.BaseTimeEntity;
import com.woojin.pochat.domain.chatroom.ChatRoom;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Lob
    @Column(columnDefinition = "BLOB")
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Builder
    public User(String username, String password, String thumbnail){

        Assert.notNull(username, "username must be not null");
        Assert.notNull(password, "password must be not null");
        Assert.notNull(thumbnail, "thubnail must be not null");

        this.username = username;
        this.password = password;
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                username.equals(user.username) &&
                password.equals(user.password);
    }
}