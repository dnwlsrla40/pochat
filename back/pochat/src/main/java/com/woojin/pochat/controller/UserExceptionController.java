package com.woojin.pochat.controller;

import com.woojin.pochat.util.error.NoUserError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(NoSuchElementException.class)
    public @ResponseBody NoUserError noUserErrorHandler(NoSuchElementException e){
        NoUserError noUser = new NoUserError();
        noUser.setMessage("User Not Exists!");
        return noUser;
    }
}
