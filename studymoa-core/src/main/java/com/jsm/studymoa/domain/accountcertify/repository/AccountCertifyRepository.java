package com.jsm.studymoa.domain.accountcertify.repository;

import com.jsm.studymoa.domain.accountcertify.AccountCertify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCertifyRepository extends JpaRepository<AccountCertify, Long>, AccountCertifyRepositoryCustom {
}
