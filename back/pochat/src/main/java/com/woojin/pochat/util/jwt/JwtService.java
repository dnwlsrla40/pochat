package com.woojin.pochat.util.jwt;

import com.woojin.pochat.domain.user.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtService {
    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    private static final String HEADER_AUTH = "jwt-auth-token";

    /*
        로그인 성공 시 사용자 정보 기반으로 jwt 생성
     */
    public String create(final User user){
        log.trace("time: {}", expireMin);
        Claims claims = Jwts.claims().setSubject("로그인 토큰");
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))    // 만기시간 
                .claim("User", user)    //.claim("seconde", "더 담고 싶은 정보
                .signWith(SignatureAlgorithm.HS256, salt.getBytes())
                .compact();
    }

    /*
        전달 받은 토큰의 유효성을 검증한다
     */
    public boolean checkValid(final String jwt){
        log.trace("토큰 점검: {}", jwt);
        // 에러 발생 하지 않으면 OK
        try{
            Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
            return true;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /*
        jwt 토큰을 분석해서 필요한 정보를 반환한다.
     */
    public Map<String, Object> get(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader(HEADER_AUTH);
        Jws<Claims> claims = null;
        try{
            claims = Jwts.parser()
                    .setSigningKey(salt.getBytes())
                    .parseClaimsJws(jwt);
        }catch (final Exception e){
            throw new RuntimeException();
        }
        log.trace("claims: {}", claims);
        return claims.getBody();
    }

}
