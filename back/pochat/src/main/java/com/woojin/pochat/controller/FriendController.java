package com.woojin.pochat.controller;

import com.woojin.pochat.domain.friend.Friend;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.dto.FriendDto;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.service.FriendService;
import com.woojin.pochat.util.error.ExistFriendError;
import com.woojin.pochat.util.error.NoUserError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FriendController {

    /*
        HttpStatus 코드
        성공
        200 - OK : 요청 성공
        201 - Created : 요청 성공 + 새로운 리소스 생성
        202 - Accepted : 클라이언트 요청은 정상 but, 아직 서버가 요청 완료 X(비동기 요청시)
        204 - No Content : 요청 정상 but, 컨텐츠 제공할 것이 없음(삭제 요청, 수정 요청 시 변경사항이 없을 경우(있으면 201로 응답) - 200응답 + body에 null,{},[],false를 추가한 응답과 다르게 body 자체가 없음)

        실패 - 오류 발생한 파라미터 위치(path, query, body), 사용자 입력 값, 에러이유 명시해주는 것이 좋다
        400 - Bad Request : 클라이언트 요청이 유효하지 않음
        401 - Unauthorized : 클라이언트가 권한이 없는 경우
     */

    private final FriendService friendService;

    /*
        method
         - friend 신청
        parameter
         - recipient
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/friend")
    public ResponseEntity<Map<String, Object>> create(@RequestBody FriendDto.FriendCreateRequestDto requestDto) {
        System.out.println(requestDto);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            Friend friend = friendService.addFriend(requestDto);
            if(friend == null){
                resultMap.put("message", "잘못된 요청입니다.");
            }
            resultMap.put("status", true);
            resultMap.put("data", friend);
            // 요청 성공 + 새로운 리소스 생성
            status = HttpStatus.CREATED;
        } catch(NoUserError e){
            resultMap.put("status", true);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.OK;
        } catch (ExistFriendError e) {
            resultMap.put("status", true);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
        method
         - friend 수락
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/friend/accept")
    public ResponseEntity<Map<String, Object>> friendAccept(@RequestBody FriendDto.FriendAcceptRequestDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            Friend acceptFriend = friendService.friendAccept(requestDto);
            resultMap.put("status", true);
            resultMap.put("data", acceptFriend);
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
        method
         - friend 신청 목록
         - 내가 신청 넣은 것
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/friend/send/list")
    public ResponseEntity<Map<String, Object>> getFriendSendList() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<Friend> friendSendList = friendService.getFriendSendList();
            resultMap.put("status", true);
            resultMap.put("data", friendSendList);
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
        method
         - friend 신청 받은 목록(아직 수락 안함)
         - 내가 신청 받은거
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/friend/request/list")
    public ResponseEntity<Map<String, Object>> friendRequestList() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<Friend> friendRequestList = friendService.getFriendRequestList();
            resultMap.put("status", true);
            resultMap.put("data", friendRequestList);
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
        method
         - friend 수락한 조회
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/friend/accept")
    public ResponseEntity<Map<String, Object>> getFriendAcceptList() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<Friend> acceptFriendList = friendService.getFriendAcceptList();
            resultMap.put("status", true);
            resultMap.put("data", acceptFriendList);
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
    /*
        method
         - 보낸 friend 취소
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/friend/cancel")
    public ResponseEntity<Map<String, Object>> friendCancel(@RequestBody FriendDto.FriendCancelRequestDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        System.out.println("canceledUser: " +requestDto.getCanceledUser());
        try{
            friendService.friendCancel(requestDto);
            resultMap.put("status", true);
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
        method
         - chatroom에 추가 할 새로운 friend 조회
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/friend/accept/{chatId}")
    public ResponseEntity<Map<String, Object>> getFriendAcceptList(@PathVariable Long chatId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<User> newMemberList = friendService.getNewMemberList(chatId);
            resultMap.put("status", true);
            resultMap.put("data", newMemberList);
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


}
