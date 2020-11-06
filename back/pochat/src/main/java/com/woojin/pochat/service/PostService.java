package com.woojin.pochat.service;

import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.domain.post.PostRepository;
import com.woojin.pochat.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

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

        // url 중복 체크
        String newUrl = requestDto.getUrl();
        if(checkUrlOverlap(newUrl)){
            newUrl = createUrlSlug(newUrl);
        }
        
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .url(newUrl)
                .isPrivate(requestDto.getIsPrivate())
                .build();

        return postRepository.save(post);
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
