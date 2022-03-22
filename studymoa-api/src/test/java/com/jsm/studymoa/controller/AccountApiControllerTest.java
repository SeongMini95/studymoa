package com.jsm.studymoa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.enums.Role;
import com.jsm.studymoa.domain.account.repository.AccountRepository;
import com.jsm.studymoa.domain.accountcertify.AccountCertify;
import com.jsm.studymoa.domain.accountcertify.repository.AccountCertifyRepository;
import com.jsm.studymoa.dto.account.request.FindPwdRequestDto;
import com.jsm.studymoa.dto.account.request.SignUpRequestDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @DisplayName("회원가입 후 이메일 인증")
    void confirmCertify() throws Exception {
        // given
        Account mockAccount = accountRepository.save(Account.builder()
                .email("email")
                .pwd(encoder.encode("pwd"))
                .nickname("nickname")
                .role(Role.GUEST)
                .isLeave(false)
                .build());

        AccountCertify mockAccountCertify = accountCertifyRepository.save(new AccountCertify(mockAccount));

        // when
        ResultActions actions = mvc.perform(get("/api/account/certify/signUp")
                .param("code", mockAccountCertify.getCode()));

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 이메일 인증 실패")
    void failConfirmCertify() throws Exception {
        // given
        Account mockAccount = accountRepository.save(Account.builder()
                .email("email")
                .pwd(encoder.encode("pwd"))
                .nickname("nickname")
                .role(Role.GUEST)
                .isLeave(false)
                .build());

        AccountCertify mockAccountCertify = accountCertifyRepository.save(new AccountCertify(mockAccount));

        // when
        ResultActions actions = mvc.perform(get("/api/account/certify/signUp")
                .param("code", "fail"));

        // then
        actions
                .andExpect(content().string(containsString("만료")));
    }

    @Test
    @DisplayName("비밀번호 변경 이메일 인증")
    void confirmCertifyFindPwd() throws Exception {
        // given
        Account mockAccount = accountRepository.save(Account.builder()
                .email("email")
                .pwd(encoder.encode("pwd"))
                .nickname("nickname")
                .role(Role.GUEST)
                .isLeave(false)
                .build());

        AccountCertify mockAccountCertify = accountCertifyRepository.save(new AccountCertify(mockAccount));

        // when
        ResultActions actions = mvc.perform(get("/api/account/certify/findPwd")
                .param("code", mockAccountCertify.getCode()));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("findPwdAccountId", mockAccount.getId()));
    }

    @Test
    @DisplayName("비밀번호 찾기 이메일 인증 후 비밀번호 변경")
    void findPwd() throws Exception {
        // given
        Account mockAccount = accountRepository.save(Account.builder()
                .email("email")
                .pwd(encoder.encode("pwd"))
                .nickname("nickname")
                .role(Role.GUEST)
                .isLeave(false)
                .build());

        FindPwdRequestDto findPwdRequestDto = FindPwdRequestDto.builder()
                .pwd("changePwd")
                .build();

        // when
        mvc.perform(post("/api/account/findPwd")
                .sessionAttr("findPwdAccountId", mockAccount.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(findPwdRequestDto)));

        Account account = accountRepository.findAll().get(0);

        // then
        assertThat(encoder.matches("changePwd", account.getPwd())).isTrue();
    }
}