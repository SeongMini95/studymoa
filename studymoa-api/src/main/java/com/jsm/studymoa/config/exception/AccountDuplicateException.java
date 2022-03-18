package com.jsm.studymoa.config.exception;

public class AccountDuplicateException extends RuntimeException{

    public AccountDuplicateException(String message) {
        super(message);
    }
}
