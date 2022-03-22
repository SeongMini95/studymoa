package com.jsm.studymoa.domain.account.repository;

import com.jsm.studymoa.domain.account.Account;

import java.util.Optional;

public interface AccountRepositoryCustom {

    Optional<Account> findByEmail(String email);
}
