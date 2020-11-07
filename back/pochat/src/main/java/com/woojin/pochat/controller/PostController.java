package com.woojin.pochat.controller;

import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public Post create(PostDto.PostCreateRequestDto requestDto) {
        return postService.create(requestDto);
    }
}
