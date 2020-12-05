package com.woojin.pochat.domain.post;

import com.woojin.pochat.domain.BaseTimeEntity;
import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;

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

    @Column(length = 150, name = "short_description")
    private String shortDescription;

    @Column(length = 100, unique = true, nullable = false)
    private String url;

    @Column(name = "is_private", columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Boolean isPrivate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(columnDefinition = "TINYINT", nullable = false)
    @ColumnDefault("0")
    private Boolean favorite;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User user;

    @Builder
    public Post(String title, String body, String shortDescription, String url, Boolean isPrivate, Boolean favorite, User user) {
        Assert.notNull(title, "title must be not null");
        Assert.notNull(body, "body must be not null");
        Assert.notNull(shortDescription, "shortDescription must be not null");
        Assert.notNull(url, "url must be not null");
        Assert.notNull(isPrivate, "isPrivate must be not null");
        Assert.notNull(user, "user must be not null");

        this.title = title;
        this.body = body;
        this.shortDescription = shortDescription;
        this.url = url;
        this.isPrivate = isPrivate;
        this.favorite = favorite;
        this.user = user;
    }

    // null 값이 들어올 경우 초기화
    @PrePersist
    public void prePersist(){
        this.isPrivate = this.isPrivate == null ? false : this.isPrivate;
        this.url = this.url == null ? "/@"+this.user.getUsername()+"/"+this.title : this.url;
        this.shortDescription = this.shortDescription == null ? (this.body.length() > 70 ? this.body.substring(0,70) : this.body) : this.shortDescription;
        this.favorite = this.favorite == null ? false : this.favorite;
    }
}
