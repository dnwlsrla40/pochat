package com.woojin.pochat.service;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroom.ChatRoomRepository;
import com.woojin.pochat.domain.friend.Friend;
import com.woojin.pochat.domain.friend.FriendRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.FriendDto;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.mapper.FriendMybatisRepository;
import com.woojin.pochat.mapper.FriendVO;
import com.woojin.pochat.util.error.ExistFriendError;
import com.woojin.pochat.util.error.ExistUserError;
import com.woojin.pochat.util.error.NoUserError;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final JwtService jwtService;

    @Autowired
    FriendMybatisRepository friendMybatisRepository;

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

    /*
        method function
         - chatRoomMember가 될 수 있는 친구 리스트 조회
        Parameter
         - id = 채팅방 id
     */
    @Transactional
    public List<User> getNewMemberList(Long id) {

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        System.out.println("=========================================" + friendMybatisRepository.getNewFriend(loginUser.getId(),id).toString());

        for (int i = 0; i < friendMybatisRepository.getNewFriend(loginUser.getId(),id).size(); i++) {
            friendMybatisRepository.getNewFriend(loginUser.getId(),id).get(i);
            System.out.println(friendMybatisRepository.getNewFriend(loginUser.getId(),id).get(i).getRecipient());
        }

        List<FriendVO> list = friendMybatisRepository.getNewFriend(loginUser.getId(),id);
        List<User> newFriendList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("get loginUser : " + loginUser.getId());
            System.out.println("get sender : " + list.get(i).getSender());
            if(loginUser.getId().compareTo(Long.parseLong(list.get(i).getSender())) == 0){
                System.out.println("1 : " + userRepository.findById(Long.parseLong(list.get(i).getRecipient())).orElseThrow(NoSuchElementException::new).toString());
                newFriendList.add(userRepository.findById(Long.parseLong(list.get(i).getRecipient())).orElseThrow(NoSuchElementException::new));
            } else if(loginUser.getId().compareTo(Long.parseLong(list.get(i).getRecipient())) == 0){
                System.out.println("2 : " + userRepository.findById(Long.parseLong(list.get(i).getRecipient())).orElseThrow(NoSuchElementException::new).toString());
                newFriendList.add(userRepository.findById(Long.parseLong(list.get(i).getSender())).orElseThrow(NoSuchElementException::new));
            } else{
                System.out.println("3");
            }
        }
        return newFriendList;
    }
}
