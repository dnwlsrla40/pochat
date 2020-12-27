package com.woojin.pochat.service;

import com.woojin.pochat.domain.friend.Friend;
import com.woojin.pochat.domain.friend.FriendRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.FriendDto;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.util.error.ExistFriendError;
import com.woojin.pochat.util.error.ExistUserError;
import com.woojin.pochat.util.error.NoUserError;
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
         - friend 신청
        Parameter
         - recipient = 친추 받는 사람
     */
    @Transactional
    public Friend addFriend(FriendDto.FriendCreateRequestDto requestDto) throws NoUserError, ExistFriendError {

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        if(userRepository.findByUsername(requestDto.getRecipientName()).isEmpty()) {
            throw new NoUserError();
        }

        User sender = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
        User recipient = userRepository.findByUsername(requestDto.getRecipientName()).orElseThrow(NoSuchElementException::new);

        Friend friend = Friend.builder()
                .isAccept(false)
                .sender(sender)
                .recipient(recipient)
                .build();

        if(sender.equals(recipient)){
            return null;
        }
        if(friendRepository.existsBySenderAndRecipient(sender, recipient) || friendRepository.existsBySenderAndRecipient(recipient,sender)){
            throw new ExistFriendError();
        }

        return friendRepository.save(friend);
    }

    /*
    method function
     - friend 수락
    */
    @Transactional
    public Friend friendAccept(FriendDto.FriendAcceptRequestDto requestDto) {

        Friend friend = friendRepository.findAllBySenderNameAndRecipientName(requestDto.getSenderName(), requestDto.getRecipientName());
        friend.setIsAccept(true);
        return friend;
    }

    /*
        method function
         - friend 신청 넣은 목록
     */
    public List<Friend> getFriendSendList() {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        return friendRepository.findAllbySenderNameAndIsAccept(loginUser);
    }


    /*
        method function
         - friend 신청 온 목록(수락하진 않음)
     */
    @Transactional
    public List<Friend> getFriendRequestList() {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        return friendRepository.findAllbyRecipientAndIsAccept(loginUser);
    }

    /*
        method function
         - friend 완료된 목록
     */
    @Transactional
    public List<Friend> getFriendAcceptList() {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        return friendRepository.findAllbyNameAndIsAccept(loginUser);
    }

    /*
        method function
         - friend 신청 취소
     */
    @Transactional
    public void friendCancel(FriendDto.FriendCancelRequestDto requestDto) {
        System.out.println("cancelUSer: " + requestDto.getCancelUser());
        System.out.println("canceledUSer: " + requestDto.getCanceledUser());
        Friend cancelFriend = friendRepository.findAllBySenderNameAndRecipientName(requestDto.getCancelUser(), requestDto.getCanceledUser());
        System.out.println("canceledFriend: " + cancelFriend);
        friendRepository.delete(cancelFriend);
    }
}
