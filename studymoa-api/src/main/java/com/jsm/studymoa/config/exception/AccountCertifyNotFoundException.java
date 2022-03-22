package com.jsm.studymoa.config.exception;

public class AccountCertifyNotFoundException extends RuntimeException {

    public AccountCertifyNotFoundException() {
        super("이미 만료된 인증입니다.");
    }
}
