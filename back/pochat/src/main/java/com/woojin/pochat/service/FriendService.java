package com.woojin.pochat.service;

import com.woojin.pochat.domain.friend.Friend;
import com.woojin.pochat.domain.friend.FriendRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.FriendDto;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    /*
        method function
         - friend 생성
        Parameter
         - recipient = 친추 받는 사람
     */
    @Transactional
    public Friend addFriend(FriendDto.FriendCreateRequestDto requestDto) {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User sender = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
        User recipient = userRepository.findByUsername(requestDto.getRecipientName()).orElseThrow(NoSuchElementException::new);

        Friend friend = Friend.builder()
                .isAccept(false)
                .sender(sender)
                .recipient(recipient)
                .build();

        return friendRepository.save(friend);
    }

    /*
        method function
         - friend 신청 목록 제공
     */
    @Transactional
    public List<Friend> getFriendList() {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        return friendRepository.findAllBySenderName(loginUser);
    }

    /*
        method function
         - friend 수락한 목록 제공
     */
    @Transactional
    public List<Friend> getFriendAcceptList() {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        return friendRepository.findAllbySenderNameAndIsAccept(loginUser);
    }
}