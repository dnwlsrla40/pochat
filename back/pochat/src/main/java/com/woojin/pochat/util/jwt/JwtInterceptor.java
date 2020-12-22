package com.woojin.pochat.util.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    private static final String HEADER_AUTH = "jwt-auth-token";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        System.out.println(req.getMethod() + " :" + req.getServletPath());

        // option 요청은 바로 통과 ??
        if(req.getMethod().equals("OPTIONS") || req.getServletPath().equals("/thumbnail")) {
            return true;
        } else{
            // req의 헤더에 token 찾기;
            String token = req.getHeader(HEADER_AUTH);
            System.out.println("token:" + token);
            if(token != null && token.length() > 0){
                // 유효성 겁사
                jwtService.checkValid(token);
                log.trace("토큰 사용 가능: {}", token);
                return true;
            } else{
                throw new RuntimeException("인증 토큰이 없습니다.");
            }
        }
    }
}
