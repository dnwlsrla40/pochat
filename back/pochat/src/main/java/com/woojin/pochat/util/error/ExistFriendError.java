package com.woojin.pochat.util.error;

public class ExistFriendError extends Exception{
    public ExistFriendError(){
        super("이미 존재하는 친구");
    }
}
