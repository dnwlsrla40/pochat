package com.woojin.pochat.controller;

import com.woojin.pochat.dto.UserDto;
import com.woojin.pochat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/signup")
    public String signUp(@RequestBody UserDto.UserCreateRequestDto requestDto){
        return userService.signUp(requestDto);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public void Login(@RequestBody UserDto.UserCreateRequestDto requestDto, HttpServletResponse res){
        userService.Login(requestDto, res);
    }
}
