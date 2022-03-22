package com.jsm.studymoa.domain.account.repository;

import com.jsm.studymoa.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
