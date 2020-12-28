package com.woojin.pochat.controller;

import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.service.PostService;
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
@RequestMapping("/post")
@RestController
public class PostController {

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


    private final PostService postService;

    /*
        method
         - post 생성
        parameter
         - title
         - body
         - shortDescription
         - isPrivate
         - chatId
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody PostDto.PostCreateRequestDto requestDto) {
        System.out.println("requestDto:" + requestDto);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            Post createdPost = postService.create(requestDto);
            resultMap.put("status", true);
            resultMap.put("data", createdPost);
            // 요청 성공 + 새로운 리소스 생성
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
         - post list 제공
        parameter
         - chatId
        response
         - status : 200 요청 성공
         - body : post list 제공
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/list/{chatId}")
    public ResponseEntity<Map<String, Object>> getPostList(@PathVariable Long chatId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            List<Post> postList = postService.getPostList(chatId);

            resultMap.put("status", true);
            resultMap.put("data", postList);
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
         - post detail 제공
            response
         - status : 200 요청 성공
         - body : post detail 제공
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPostDetail(@PathVariable Long id){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            Post postDetail = postService.getPostDetail(id);

            resultMap.put("status", true);
            resultMap.put("data", postDetail);
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
         - post update
        parameter
         - title
         - body
         - shortDescription
         - isPrivate
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody PostDto.PostUpdateRequestDto requestDto) {
        System.out.println("requestDto:" + requestDto);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            Post updatedPost = postService.update(requestDto);
            resultMap.put("status", true);
            resultMap.put("data", updatedPost);
            // 요청 성공 + 새로운 리소스 생성
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
         - post 삭제
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long postId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            postService.delete(postId);
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
