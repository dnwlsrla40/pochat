package com.woojin.pochat.domain.user;

import com.woojin.pochat.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(String username, String password){

        Assert.notNull(username, "username must be not null");
        Assert.notNull(password, "password must be not null");

        this.username = username;
        this.password = password;
    }
}
