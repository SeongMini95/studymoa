package com.jsm.studymoa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.repository.AccountRepository;
import com.jsm.studymoa.domain.accountcertify.repository.AccountCertifyRepository;
import com.jsm.studymoa.dto.account.request.SignUpRequestDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountApiControllerTest {

    private final Logger logger = LogManager.getLogger(AccountApiControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountCertifyRepository accountCertifyRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
        logger.info("AccountCertify delete all");
        accountCertifyRepository.deleteAll();

        logger.info("Account delete all");
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        // given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("email")
                .pwd("pwd")
                .nickname("nickname")
                .build();

        // when
        mvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequestDto)))
                .andExpect(status().isOk());

        Account account = accountRepository.findAll().get(0);

        // then
        assertThat(account.getEmail()).isEqualTo(signUpRequestDto.getEmail());
        assertThat(encoder.matches(signUpRequestDto.getPwd(), account.getPwd())).isTrue();
        assertThat(account.getNickname()).isEqualTo(signUpRequestDto.getNickname());
    }
}