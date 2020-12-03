package com.woojin.pochat.domain.friend;

import com.woojin.pochat.domain.BaseTimeEntity;
import com.woojin.pochat.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Friend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_accept", nullable = false)
    private Boolean isAccept;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "sender", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "recipient", nullable = false)
    private User recipient;

    @Builder
    public Friend(Boolean isAccept, User sender, User recipient) {
        Assert.notNull(isAccept, "isAccept must be not null");
        Assert.notNull(sender, "sender must be not null");
        Assert.notNull(recipient, "recipient must be not null");

        this.isAccept = isAccept;
        this.sender = sender;
        this.recipient = recipient;
    }

    @PrePersist
    public void prePersist(){
        this.isAccept = this.isAccept == null ? false : this.isAccept;
    }
}
