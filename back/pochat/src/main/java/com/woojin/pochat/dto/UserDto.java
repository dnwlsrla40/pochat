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
        private String thumbnail;

        public User toEntity(){
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .thumbnail(this.thumbnail)
                    .build();
        }
    }
}
