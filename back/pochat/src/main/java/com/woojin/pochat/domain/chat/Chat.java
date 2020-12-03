package com.woojin.pochat.domain.chat;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatType type;

    // 후에 chatroom의 id로 변경 (ChatRoom chatroomId)
    @Column
    private String roomId;

    // 후에 Member의 id로 변경 (Memeber memberId)
    @Column(length = 100, nullable = false, unique = true)
    private String sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;
}
