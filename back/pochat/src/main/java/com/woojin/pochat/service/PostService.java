package com.woojin.pochat.service;

import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.domain.post.PostRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    /*
        method function
         - post 생성
        Parameter
         - title = 사용자 입력
         - body = 사용자 입력
         - url = 시스템에서 title 값 + 중복시 제거할 slug
         - isPrivate = 그룹원에게 보일 지 자신에게만 보일 지 사용자 입력
     */
    @Transactional
    public Post create(PostDto.PostCreateRequestDto requestDto) {

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User writeUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        // url 중복 체크
        String newUrl = requestDto.getUrl();
        if(checkUrlOverlap(newUrl)){
            newUrl = createUrlSlug(newUrl);
        }
        
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .shortDescription(requestDto.getShortDescription())
                .url(newUrl)
                .isPrivate(requestDto.getIsPrivate())
                .user(writeUser)
                .build();

        return postRepository.save(post);
    }
    
    /*
        method
         - 즐겨찾기 업데이트
        Parameter
         - 즐겨찾기 될 post title
         - post의 즐겨찾기 상태
     */
    @Transactional
    public Post updateFavorite(PostDto.PostUpdateFavoriteRequestDto requestDto){
        Post post = postRepository.findById(requestDto.getId()).orElseThrow(NoSuchElementException::new);
        post.setFavorite(!post.getFavorite());
        return post;
    }

    /*
        method
         - 즐겨찾기 포스트 목록 제공
     */
    @Transactional
    public List<Post> getPostFavoriteList() {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        return postRepository.findAllFavoritePostByUsername(username);
    }

    /*
        method
         - post list 제공
     */
    @Transactional
    public List<Post> getPostList() {

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");
        
        return postRepository.findAllByUsername(username);
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

    // url 중복 체크
    public Boolean checkUrlOverlap(String url){
        return postRepository.findByUrl(url) != null ? true : false;
    }

    // url 중복 시 urlSlug 생성
    public String createUrlSlug(String url){
        String urlSlug;
        String newUrl;
        do {
            urlSlug = RandomStringUtils.randomAlphanumeric(5);
            newUrl = url+"-"+urlSlug;
        }while(postRepository.findByUrl(newUrl) != null);
        return newUrl;
    }

}
