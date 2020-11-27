package com.woojin.pochat.controller;

import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.dto.UserDto;
import com.woojin.pochat.service.UserService;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private JwtService jwtService;

    private final UserService userService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/signup")
    public String signUp(@RequestBody UserDto.UserCreateRequestDto requestDto){
        return userService.signUp(requestDto);
    }


//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @PostMapping("/login")
//    public void Login(@RequestBody UserDto.UserCreateRequestDto requestDto, HttpServletResponse res){
//        userService.Login(requestDto, res);
//    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto.UserCreateRequestDto requestDto, HttpServletResponse res){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try{
            User loginUser = userService.signin(requestDto);
            System.out.println("controller loginUser: " + loginUser);
            String token = jwtService.create(loginUser);

            res.setHeader("jwt-auth-token",token);

            resultMap.put("status", true);
            resultMap.put("data", loginUser);
            status = HttpStatus.ACCEPTED;
        } catch(RuntimeException e) {
            log.error("로그인 실패", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{username}")
    public User getUserInfo(@PathVariable(name = "username") String username){
        return userService.getUserInfo(username);
    }
}
