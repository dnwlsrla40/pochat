package com.woojin.pochat.service;

import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.UserDto;
import com.woojin.pochat.util.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String signUp(UserDto.UserCreateRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = Hashing.hashingPassword(requestDto.getPassword());

        if(username.equals("") || password.equals(""))
            return "failed";
        if(!userRepository.findByUsername(username).isEmpty())
            return "failed";

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        userRepository.save(user);
        return "success";
    }

//    @Transactional
//    public void Login (UserDto.UserCreateRequestDto requestDto, HttpServletResponse res) {
//        String password = Hashing.hashingPassword(requestDto.getPassword());
//        Optional<User> user  = userRepository.findByUsernameAndPassword(requestDto.getUsername(), password);
//        System.out.println(user.get().getUsername());
//        if(!user.isEmpty()){
//            Cookie cookie = new Cookie("username", user.get().getUsername());
//            cookie.setMaxAge(3600);
//            res.addCookie(cookie);
//        }
//    }

    @Transactional
    public User signin(UserDto.UserCreateRequestDto requestDto){
        String password = Hashing.hashingPassword(requestDto.getPassword());
        User loginUser  = userRepository.findByUsernameAndPassword(requestDto.getUsername(), password);
        System.out.println("loginUser:" + loginUser);
        return loginUser;
    }

    @Transactional
    public User getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(IllegalAccessError::new);
        return user;
    }
}
