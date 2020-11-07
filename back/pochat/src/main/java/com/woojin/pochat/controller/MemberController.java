package com.woojin.pochat.controller;

import com.woojin.pochat.domain.member.Member;
import com.woojin.pochat.dto.MemberDto;
import com.woojin.pochat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public Member signup(@RequestBody MemberDto.MemberCreateRequestDto requestDto){
        System.out.println("signup");
        return memberService.signup(requestDto);
    }
}
