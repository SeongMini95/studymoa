package com.jsm.studymoa.domain.account.repository;

import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.QAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.jsm.studymoa.domain.account.QAccount.account;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public Optional<Account> findByEmail(String email) {
        return Optional.ofNullable(factory
                .selectFrom(account)
                .where(account.email.eq(email),
                        account.isLeave.eq(false))
                .fetchOne());
    }
}
