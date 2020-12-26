package com.woojin.pochat.domain.chat;

import com.woojin.pochat.domain.BaseTimeEntity;
import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
public class Chat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String chatRoom;

    @Column(length = 100, nullable = false)
    private String sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "sender_thumbnail", nullable = false)
    private String senderThumbnail;

    @Builder
    public Chat(String chatRoom, String sender, String message, String senderThumbnail){

        Assert.notNull(chatRoom, "chatRoom must be not null");
        Assert.notNull(sender, "sender must be not null");
        Assert.notNull(message, "message must be not null");

        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
        this.senderThumbnail = senderThumbnail;
    }
}
