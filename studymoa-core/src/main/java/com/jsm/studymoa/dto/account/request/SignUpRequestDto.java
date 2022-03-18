package com.jsm.studymoa.dto.account.request;

import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignUpRequestDto {

    private String email;
    private String pwd;
    private String nickname;

    @Builder
    public SignUpRequestDto(String email, String pwd, String nickname) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
    }

    public Account toEntity(String encPwd) {
        return Account.builder()
                .email(email)
                .pwd(encPwd)
                .nickname(nickname)
                .role(Role.GUEST)
                .isLeave(false)
                .build();
    }
}
