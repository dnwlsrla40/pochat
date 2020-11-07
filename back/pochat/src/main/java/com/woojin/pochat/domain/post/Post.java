package com.woojin.pochat.domain.post;

import com.woojin.pochat.domain.BaseTimeEntity;
import com.woojin.pochat.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(length = 100, unique = true, nullable = false)
    private String url;

    @Column(name = "is_private", columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Boolean isPrivate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "release_date")
    private Date releaseDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(String title, String body, String url, Boolean isPrivate, Member member) {
        this.title = title;
        this.body = body;
        this.url = url;
        this.isPrivate = isPrivate;
        this.member = member;
    }
}
