package com.woojin.pochat.util;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessionList;

    public ChatWebSocketHandler(){
        sessionList = new ArrayList<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessionList.add(session);
        System.out.println("======================="+session.getAttributes().toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        sessionList.remove(session);
    }

    public void sendMessage() {

    }
}
