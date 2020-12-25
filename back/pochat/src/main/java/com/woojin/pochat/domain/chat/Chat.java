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

    // 후에 chatroom의 id로 변경 (ChatRoom chatroomId)
    @Column(length = 100, nullable = false)
    private String chatRoom;

    // 후에 Member의 id로 변경 (Memeber memberId)
    @Column(length = 100, nullable = false)
    private String sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Builder
    public Chat(String chatRoom, String sender, String message){

        Assert.notNull(chatRoom, "chatRoom must be not null");
        Assert.notNull(sender, "sender must be not null");
        Assert.notNull(message, "message must be not null");

        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
    }
}
