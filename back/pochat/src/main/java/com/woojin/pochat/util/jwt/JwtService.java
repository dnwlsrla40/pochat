package com.woojin.pochat.util.jwt;

import com.woojin.pochat.domain.user.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtService {
    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    /*
        로그인 성공 시 사용자 정보 기반으로 jwt 생성
     */
    public String create(final User user){
        log.trace("time: {}", expireMin);
        final JwtBuilder builder = Jwts.builder();
        
        // 헤더 설정
        builder.setHeaderParam("typ", "JWT");

        // payload 설정
        builder.setSubject("로그인토큰")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))    // 만기시간 
                .claim("User", user);    //.claim("seconde", "더 담고 싶은 정보
        
        // 암호화
        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        // 직렬화
        final String jwt = builder.compact();
        log.debug("토큰 발행: {}", jwt);
        return jwt;
    }

    /*
        전달 받은 토큰의 유효성을 검증한다
     */
    public void checkValid(final String jwt){
        log.trace("토큰 점검: {}", jwt);
        // 에러 발생 하지 않으면 OK
        Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
    }

    /*
        jwt 토큰을 분석해서 필요한 정보를 반환한다.
     */
    public Map<String, Object> get(final String jwt){
        Jws<Claims> claims = null;
        try{
            claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        }catch (final Exception e){
            throw new RuntimeException();
        }

        log.trace("claims: {}", claims);
        return claims.getBody();
    }

}
