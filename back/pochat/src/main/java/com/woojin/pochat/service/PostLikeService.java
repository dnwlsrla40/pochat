package com.woojin.pochat.service;

import com.woojin.pochat.domain.post.Post;
import com.woojin.pochat.domain.post.PostRepository;
import com.woojin.pochat.domain.postlike.PostLike;
import com.woojin.pochat.domain.postlike.PostLikeRepository;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.PostDto;
import com.woojin.pochat.dto.PostLikeDto;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    /*
        method
         - 즐겨찾기 업데이트
        Parameter
         - 즐겨찾기 될 post title
         - post의 즐겨찾기 상태
     */
    @Transactional
    public PostLike createPostLike(PostLikeDto.PostLikeCreateRequestDto requestDto){
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        Post likedPost = postRepository.findById(requestDto.getId()).orElseThrow(NoSuchElementException::new);

        PostLike postLike = PostLike.builder()
                .user(loginUser)
                .post(likedPost)
                .build();

        likedPost.setIsFavorite(true);

        return postLikeRepository.save(postLike);
    }

    /*
        method
         - 즐겨찾기 포스트 목록 제공
        Parameter
         - 채팅방 id
     */
    @Transactional
    public List<Post> getPostLikeList(Long chatId) {
        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        List<Post> FavoriteList = postLikeRepository.findAllPostByChatRoomAndUser(chatId, loginUser);
        List<Post> newFavoriteList = new ArrayList<>();

        for (int i = 0; i < FavoriteList.size(); i++) {
            Post currentFavorite = FavoriteList.get(i);
            if(currentFavorite.getIsPrivate() && loginUser.equals(currentFavorite.getUser())){
                newFavoriteList.add(currentFavorite);
            } else if(!currentFavorite.getIsPrivate())
                newFavoriteList.add(currentFavorite);
        }
        return newFavoriteList;
    }

    /*
        method
         - postLike 삭제
        Parameter
         - post id
     */
    @Transactional
    public void delete(Long postId) {

        // 접속중인 username 가져오기
        Map map = (Map)jwtService.get().get("User");
        String username = (String)map.get("username");

        User loginUser = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        Post deletedPost = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);

        PostLike deletedPostLike = postLikeRepository.findByUserAndPost(loginUser, deletedPost);

        deletedPost.setIsFavorite(false);

        postLikeRepository.delete(deletedPostLike);
    }
}
