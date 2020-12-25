package com.woojin.pochat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class WebSocketChatMessage {
    private String type;
    private String message;
    private String username;
    private String channel;

    public WebSocketChatMessage() {

    }
}
