package com.woojin.pochat.domain.chatroommember;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ChatRoomMember(ChatRoom chatRoom, User user){

        Assert.notNull(chatRoom, "chatRoom must be not null");
        Assert.notNull(user, "user must be not null");

        this.chatRoom = chatRoom;
        this.user = user;
    }
}
