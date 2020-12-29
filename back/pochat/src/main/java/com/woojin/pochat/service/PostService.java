package com.woojin.pochat.service;

import com.woojin.pochat.domain.chatroom.ChatRoom;
import com.woojin.pochat.domain.chatroom.ChatRoomRepository;
import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.domain.post.PostRepository;
import com.woojin.pochat.domain.postlike.PostLikeRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PostLikeRepository postLikeRepository;
    private final JwtService jwtService;

    /*
        method function
         - post 생성
        Parameter
         - title = 사용자 입력
         - body = 사용자 입력
         - url = 시스템에서 title 값 + 중복시 제거할 slug
         - isPrivate = 그룹원에게 보일 지 자신에게만 보일 지 사용자 입력
         - chatId = 채팅방 Id
     */
    @Transactional
    public Post create(PostDto.PostCreateRequestDto requestDto) {

        System.out.println(requestDto.getShortDescription());

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User writeUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        // 접속한 채팅방 가져오기
        ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getChatId()).orElseThrow(NoSuchElementException::new);
        
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .shortDescription(requestDto.getShortDescription())
                .isPrivate(requestDto.getIsPrivate())
                .user(writeUser)
                .chatRoom(chatRoom)
                .build();

        return postRepository.save(post);
    }

    /*
        method
         - post list 제공
        Parameter
         - 채팅방 id
     */
    @Transactional
    public List<Post> getPostList(Long chatId) {

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        // 접속한 채팅방 가져오기
//        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(NoSuchElementException::new);

        List<Post> postList = postRepository.findAllByChatRoom(chatId);
        List<Post> newPostList = new ArrayList<>();

        for (int i = 0; i < postList.size(); i++) {
            Post currentPost = postList.get(i);
            if(currentPost.getIsPrivate() && loginUser.equals(currentPost.getUser())){
                newPostList.add(currentPost);
            } else if(!currentPost.getIsPrivate())
                newPostList.add(currentPost);
        }
        return newPostList;
    }

    /*
        method
         - post detail 제공
        parameter
         - post id
     */
    @Transactional
    public Post getPostDetail(Long id) {
        return postRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Post update(PostDto.PostUpdateRequestDto requestDto) {
        System.out.println("===========================" + requestDto.getId());
//        Long Id = Long.parseLong(requestDto.getId());
        Post post = postRepository.findById(requestDto.getId()).orElseThrow(NoSuchElementException::new);

        post.update(requestDto.getTitle(), requestDto.getShortDescription(), requestDto.getBody(), requestDto.getIsPrivate());

        return post;
    }

    @Transactional
    public void delete(Long postId) {

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        Post deletedPost = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        if(postLikeRepository.findByUserAndPost(loginUser, deletedPost) != null){
            postLikeRepository.delete(postLikeRepository.findByUserAndPost(loginUser, deletedPost));
        }
        System.out.println("deletedPost: " + deletedPost);
        postRepository.delete(deletedPost);
    }

}
