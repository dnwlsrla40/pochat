package com.woojin.pochat.domain.chatroom;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Builder
    public ChatRoom(String name){

        Assert.notNull(name, "name must be not null");

        this.name = name;
    }
}
