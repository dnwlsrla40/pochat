package com.woojin.pochat.mapper;

import com.woojin.pochat.domain.friend.Friend;
import com.woojin.pochat.domain.user.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class FriendMybatisRepository {

    protected static final String NAMESPACE = "com.woojin.pochat.mapper.FriendMapper.";

    @Autowired
    private SqlSession sqlSession;

    public List<FriendVO> getNewFriend(Long chatRoomId, Long userId){
        FriendParameter param = new FriendParameter(chatRoomId, userId);
        return sqlSession.selectList(NAMESPACE + "getNewFriend", param);
    }
}
