package com.woojin.pochat.dto;

import com.woojin.pochat.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

    @NoArgsConstructor
    @Data
    public static class UserCreateRequestDto {
        private String username;
        private String password;

        public User toEntity(){
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .build();
        }
    }
}
