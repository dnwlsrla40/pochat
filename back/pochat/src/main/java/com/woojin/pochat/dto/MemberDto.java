package com.woojin.pochat.dto;

import com.woojin.pochat.domain.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {

    @NoArgsConstructor
    @Data
    public static class MemberCreateRequestDto {
        private String username;
        private String password;

        public Member toEntity(){
            return Member.builder()
                    .username(this.username)
                    .password(this.password)
                    .build();
        }
    }
}
