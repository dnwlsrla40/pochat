package com.woojin.pochat.service;

import com.woojin.pochat.domain.user.Role;
import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.domain.user.UserRepository;
import com.woojin.pochat.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDto.UserCreateRequestDto requestDto){
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return userRepository.save(requestDto.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userWrapper = userRepository.findByUsername(username);
        User user = userWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        // User Role 추가
        authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
