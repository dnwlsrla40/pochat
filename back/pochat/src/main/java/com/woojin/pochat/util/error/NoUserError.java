package com.woojin.pochat.util.error;

import lombok.Setter;

public class NoUserError extends Exception{
    public NoUserError(){
        super("존재하지 않는 유저 입니다.");
    }
}
