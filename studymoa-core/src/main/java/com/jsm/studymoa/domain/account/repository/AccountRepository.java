package com.jsm.studymoa.domain.account.repository;

import com.jsm.studymoa.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
