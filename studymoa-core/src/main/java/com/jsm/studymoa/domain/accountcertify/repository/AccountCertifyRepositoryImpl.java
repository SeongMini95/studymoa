package com.jsm.studymoa.domain.accountcertify.repository;

import com.jsm.studymoa.domain.accountcertify.AccountCertify;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.jsm.studymoa.domain.accountcertify.QAccountCertify.accountCertify;

@RequiredArgsConstructor
public class AccountCertifyRepositoryImpl implements AccountCertifyRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public Optional<AccountCertify> findByCode(String code, LocalDateTime now) {
        return Optional.ofNullable(factory
                .selectFrom(accountCertify)
                .where(accountCertify.code.eq(code),
                        accountCertify.createDatetime.before(now),
                        accountCertify.expireDatetime.after(now),
                        accountCertify.isUse.eq(false))
                .fetchOne());
    }
}
