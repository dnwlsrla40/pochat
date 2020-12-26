package com.woojin.pochat.service;

import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.UserDto;
import com.woojin.pochat.util.Hashing;
import com.woojin.pochat.util.UploadThumbnailUtils;
import com.woojin.pochat.util.error.ExistUserError;
import com.woojin.pochat.util.error.NoUserError;
import com.woojin.pochat.util.jwt.JwtService;
import javassist.bytecode.ExceptionTable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${resources.location}")
    private String thumbnailPath;

    @Transactional
    public User signUp(UserDto.UserCreateRequestDto requestDto) throws ExistUserError {
        String username = requestDto.getUsername();
        String password = Hashing.hashingPassword(requestDto.getPassword());
        System.out.println(requestDto.getThumbnail());

        if(!userRepository.findByUsername(username).isEmpty()) {
            throw new ExistUserError();
        } else if(requestDto.getThumbnail() == null){
            requestDto.setThumbnail("/default/default.PNG");
        }
        System.out.println(requestDto.getThumbnail());

        User user = User.builder()
                .username(username)
                .password(password)
                .thumbnail(requestDto.getThumbnail())
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User signin(UserDto.UserCreateRequestDto requestDto) throws NoUserError{
        String password = Hashing.hashingPassword(requestDto.getPassword());
        System.out.println(requestDto.getUsername());
        System.out.println(password);
        System.out.println(userRepository.findByUsernameAndPassword(requestDto.getUsername(), password));
        if(userRepository.findByUsernameAndPassword(requestDto.getUsername(), password).isEmpty()) {
            throw new NoUserError();
        }
        User loginUser  = userRepository.findByUsernameAndPassword(requestDto.getUsername(), password).orElseThrow(NoSuchElementException::new);
        return loginUser;
    }

    @Transactional
    public User getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(IllegalAccessError::new);
        return user;
    }

    @Transactional
    public User updateThumbnail(UserDto.UserUpdateThumbnailRequestDto requestDto){
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
        loginUser.updateThumbnail(requestDto.getThumbnail());
        return loginUser;
    }
}
