package com.jsm.studymoa.config.security.dto;

import com.jsm.studymoa.domain.account.Account;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionAccount implements Serializable {

    private Long id;
    private String email;
    private String nickname;

    public SessionAccount(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.nickname = account.getNickname();
    }
}
