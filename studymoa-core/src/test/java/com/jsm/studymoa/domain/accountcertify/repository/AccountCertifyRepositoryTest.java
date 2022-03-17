package com.jsm.studymoa.domain.accountcertify.repository;

import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.enums.Role;
import com.jsm.studymoa.domain.account.repository.AccountRepository;
import com.jsm.studymoa.domain.accountcertify.AccountCertify;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountCertifyRepositoryTest {

    private final Logger logger = LogManager.getLogger(AccountCertifyRepositoryTest.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountCertifyRepository accountCertifyRepository;

    private Account mockAccount;

    @BeforeEach
    void setUp() {
        if (mockAccount == null) {
            mockAccount = accountRepository.save(Account.builder()
                    .email("email")
                    .pwd("pwd")
                    .nickname("nickname")
                    .role(Role.GUEST)
                    .isLeave(false)
                    .build());
        }
    }

    @AfterEach
    void tearDown() {
        logger.info("AccountCertify delete all");
        accountCertifyRepository.deleteAll();
    }

    @Test
    @DisplayName("AccountCertify insert repository 테스트")
    void insertAccountCertify() {
        // given
        AccountCertify mockAccountCertify = AccountCertify.builder()
                .account(mockAccount)
                .code("code")
                .expireDatetime(LocalDateTime.now())
                .isUse(false)
                .build();

        // when
        accountCertifyRepository.save(mockAccountCertify);

        AccountCertify accountCertify = accountCertifyRepository.findAll().get(0);

        // then
        assertThat(accountCertify.getCode()).isEqualTo(mockAccountCertify.getCode());
        assertThat(accountCertify.getExpireDatetime()).isEqualTo(mockAccountCertify.getExpireDatetime());
        assertThat(accountCertify.isUse()).isEqualTo(mockAccountCertify.isUse());
    }
}