package com.github.order.user.domain;

import com.github.order.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "users",
        uniqueConstraints =
        @UniqueConstraint(
                name = "unq_user_email",
                columnNames = {"email"}
        ))
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 80, nullable = false)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int loginCount;

    @Column(nullable = true)
    private LocalDateTime lastLoginAt;

    static public User create(String name, String email, String password) {
        User user = new User();
        user.name = name;
        user.email = email;
        user.password = password;

        return user;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void addLoginCount() {
        this.loginCount ++;
    }

    public void setLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

}
