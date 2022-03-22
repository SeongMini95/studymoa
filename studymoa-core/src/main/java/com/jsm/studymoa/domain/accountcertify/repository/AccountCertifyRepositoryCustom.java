package com.jsm.studymoa.domain.accountcertify.repository;

import com.jsm.studymoa.domain.accountcertify.AccountCertify;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AccountCertifyRepositoryCustom {

    Optional<AccountCertify> findByCode(String code, LocalDateTime now);
}
