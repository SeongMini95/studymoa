package com.jsm.studymoa.domain.account;

import com.jsm.studymoa.domain.BaseTimeEntity;
import com.jsm.studymoa.domain.account.converter.RoleConverter;
import com.jsm.studymoa.domain.account.enums.Role;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
@DynamicUpdate
@Table(name = "account")
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "pwd", nullable = false, length = 60)
    private String pwd;

    @Column(name = "nickname", nullable = false, length = 15)
    private String nickname;

    @Column(name = "profile")
    private String profile;

    @Column(name = "introduce", length = 45)
    private String introduce;

    @Column(name = "link", length = 150)
    private String link;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "is_leave", nullable = false)
    private boolean isLeave;

    @Builder
    public Account(Long id, String email, String pwd, String nickname, String profile, String introduce, String link, Role role, boolean isLeave) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
        this.link = link;
        this.role = role;
        this.isLeave = isLeave;
    }

    public void completeSignup() {
        this.role = Role.USER;
    }

    public void changePwd(String encodedPwd) {
        this.pwd = encodedPwd;
    }
}
