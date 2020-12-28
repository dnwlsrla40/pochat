package com.woojin.pochat.controller;

import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.domain.postlike.PostLike;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.dto.PostLikeDto;
import com.woojin.pochat.service.PostLikeService;
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
public class PostLikeController {

    private final PostLikeService postLikeService;

    /*
        method
         - 즐겨찾기 된 post 목록
         parameter
         - chatId
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/postlike/{chatId}")
    public ResponseEntity<Map<String, Object>> getPostFavoriteList(@PathVariable Long chatId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<Post> postFavoriteList = postLikeService.getPostLikeList(chatId);
            resultMap.put("status", true);
            resultMap.put("data", postFavoriteList);
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
         - post에 즐겨찾기 생성
        parameter
         - 즐겨찾기 추가 할 postId
        response
         - status : 201 Created 즐겨찾기 상태 변경
         - body : 즐겨찾기한 post
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/postlike")
    public ResponseEntity<Map<String, Object>> createPostLike(@RequestBody PostLikeDto.PostLikeCreateRequestDto requestDto){
        System.out.println("requestDto: " + requestDto);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            PostLike postLike = postLikeService.createPostLike(requestDto);

            resultMap.put("status", true);
            resultMap.put("data", postLike);
            status = HttpStatus.CREATED;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
    method
     - postLike 삭제
 */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("postlike/delete/{postId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long postId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            postLikeService.delete(postId);
            resultMap.put("status", true);
            status = HttpStatus.OK;
        } catch(RuntimeException e) {
            log.error("", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
