package com.woojin.pochat.controller;

import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.dto.UserDto;
import com.woojin.pochat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private UserService userService;

    @PostMapping("/signup")
    public User signup(UserDto.UserCreateRequestDto requestDto){
        return userService.signup(requestDto);
    }
}
