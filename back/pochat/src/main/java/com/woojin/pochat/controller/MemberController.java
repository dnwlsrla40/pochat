package com.woojin.pochat.controller;

import com.woojin.pochat.domain.member.Member;
import com.woojin.pochat.dto.AuthenticationRequestDto;
import com.woojin.pochat.dto.AuthenticationTokenDto;
import com.woojin.pochat.dto.MemberDto;
import com.woojin.pochat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    AuthenticationManager authenticationManager;

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/signup")
    public Member signup(@RequestBody MemberDto.MemberCreateRequestDto requestDto){
        System.out.println("signup");
        return memberService.signup(requestDto);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public AuthenticationTokenDto login(@RequestBody AuthenticationRequestDto authenticationRequestDto, HttpSession session){
        System.out.println("login controller");
        System.out.println(authenticationRequestDto);;
        String username = authenticationRequestDto.getUsername();
        String password = passwordEncoder().encode(authenticationRequestDto.getPassword());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        Member member = memberService.readUser(username);
        return new AuthenticationTokenDto(member.getUsername(), session.getId());
    }
}
