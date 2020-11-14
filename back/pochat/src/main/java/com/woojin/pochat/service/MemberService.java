package com.woojin.pochat.service;

import com.woojin.pochat.domain.member.Role;
import com.woojin.pochat.domain.member.Member;
import com.woojin.pochat.domain.member.MemberRepository;
import com.woojin.pochat.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public Member signup(MemberDto.MemberCreateRequestDto requestDto){
        requestDto.setPassword(passwordEncoder().encode(requestDto.getPassword()));
        return memberRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Member readUser(String username) {
        Optional<Member> optional = memberRepository.findByUsername(username);
        Member member = optional.get();
        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userWrapper = memberRepository.findByUsername(username);
        Member member = userWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        // User Role 추가
        authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));

        return new org.springframework.security.core.userdetails.User(member.getUsername(), member.getPassword(), authorities);
    }
}
