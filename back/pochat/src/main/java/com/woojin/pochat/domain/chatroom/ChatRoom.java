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

    @Column(length = 100, unique = true)
    private String name;

    @Builder
    public ChatRoom(String name){

        Assert.notNull(name, "name must be not null");

        this.name = name;
    }
}
