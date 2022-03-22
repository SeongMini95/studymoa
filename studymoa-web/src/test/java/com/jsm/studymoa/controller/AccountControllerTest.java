package com.jsm.studymoa.controller;

import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.enums.Role;
import com.jsm.studymoa.domain.account.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AccountControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {
        // given
        accountRepository.save(Account.builder()
                .email("email@naver.com")
                .pwd(encoder.encode("test"))
                .nickname("nickname")
                .role(Role.GUEST)
                .isLeave(false)
                .build());

        // when
        ResultActions actions = mvc.perform(formLogin()
                .loginProcessingUrl("/account/login?returnUrl=/test")
                .user("email", "email@naver.com")
                .password("test"));

        // then
        actions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/test"));
    }

    @Test
    @DisplayName("로그인 실패")
    void failLogin() throws Exception {
        // given
        accountRepository.save(Account.builder()
                .email("email@naver.com")
                .pwd(encoder.encode("test"))
                .nickname("nickname")
                .role(Role.GUEST)
                .isLeave(false)
                .build());

        // when
        ResultActions actions = mvc.perform(formLogin()
                .loginProcessingUrl("/account/login")
                .user("email", "email@naver.com")
                .password("test2"));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/account/login"));
    }
}