package com.jsm.studymoa.config.exception;

public class FindPwdAccountIdNotFoundException extends RuntimeException {

    public FindPwdAccountIdNotFoundException() {
        super("비정상적인 접근 입니다.");
    }
}
