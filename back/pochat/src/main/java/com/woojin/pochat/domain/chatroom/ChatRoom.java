package com.woojin.pochat.domain.chatroom;

import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;
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

    @Column(name = "routing_key")
    private String routingKey;

    @Column(name = "queue_name")
    private String queueName;

    @Builder
    public ChatRoom(String name){

        Assert.notNull(name, "name must be not null");

        this.name = name;
    }

    public void update(String queueName, String routingKey){
        this.queueName = queueName;
        this.routingKey = routingKey;
    }
}
