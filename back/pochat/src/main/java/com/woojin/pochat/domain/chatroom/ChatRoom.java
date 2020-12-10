package com.woojin.pochat.domain.chatroom;

import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class ChatRoom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<User> roomMember;

    @Builder
    public ChatRoom(String name, List<User> roomMember){

        Assert.notNull(name, "name must be not null");
        Assert.notNull(roomMember, "roomMember must be not null");
        this.name = name;
        this.roomMember = roomMember;
    }
}
