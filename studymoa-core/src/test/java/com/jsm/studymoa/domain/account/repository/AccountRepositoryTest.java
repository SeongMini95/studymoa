package com.jsm.studymoa.domain.account.repository;

import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    private final Logger logger = LogManager.getLogger(AccountRepositoryTest.class);

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        logger.info("Account delete all");
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Account insert repository 테스트")
    void insertAccount() {
        // given
        Account mockAccount = Account.builder()
                .email("email")
                .pwd("pwd")
                .nickname("nickname")
                .role(Role.GUEST)
                .isLeave(false)
                .build();

        // when
        accountRepository.save(mockAccount);

        Account account = accountRepository.findAll().get(0);

        // then
        assertThat(account.getEmail()).isEqualTo(mockAccount.getEmail());
        assertThat(account.getPwd()).isEqualTo(mockAccount.getPwd());
        assertThat(account.getNickname()).isEqualTo(mockAccount.getNickname());
        assertThat(account.getRole()).isEqualTo(mockAccount.getRole());
        assertThat(account.isLeave()).isEqualTo(mockAccount.isLeave());
    }
}