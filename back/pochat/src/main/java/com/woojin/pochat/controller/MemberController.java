package com.woojin.pochat.controller;

import com.woojin.pochat.domain.member.Member;
import com.woojin.pochat.dto.AuthenticationRequestDto;
import com.woojin.pochat.dto.AuthenticationTokenDto;
import com.woojin.pochat.dto.MemberDto;
import com.woojin.pochat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public Member signup(@RequestBody MemberDto.MemberCreateRequestDto requestDto){
        System.out.println("signup");
        return memberService.signup(requestDto);
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public AuthenticationTokenDto login(@RequestBody AuthenticationRequestDto authenticationRequestDto, HttpSession session){
        String username = authenticationRequestDto.getUsername();
        String password = authenticationRequestDto.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        Member member = memberService.readUser(username);
        return new AuthenticationTokenDto(member.getUsername(), "", session.getId());
    }
}
