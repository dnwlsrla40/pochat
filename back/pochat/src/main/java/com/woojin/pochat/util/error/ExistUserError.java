package com.woojin.pochat.util.error;

public class ExistUserError extends Exception{
    public ExistUserError(){
        super("이미 존재하는 유저");
    }
}
