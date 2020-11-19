package com.woojin.pochat.domain;

import lombok.Data;

@Data
public class WebSocketChatMessage {
    private String type;
    private String message;
    private String username;

}
