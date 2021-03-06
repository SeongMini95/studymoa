package com.jsm.studymoa.service;

import com.jsm.studymoa.config.exception.AccountCertifyNotFoundException;
import com.jsm.studymoa.config.exception.AccountDuplicateException;
import com.jsm.studymoa.config.exception.FindPwdAccountIdNotFoundException;
import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.repository.AccountRepository;
import com.jsm.studymoa.domain.accountcertify.AccountCertify;
import com.jsm.studymoa.domain.accountcertify.repository.AccountCertifyRepository;
import com.jsm.studymoa.dto.account.request.FindPwdRequestDto;
import com.jsm.studymoa.dto.account.request.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountCertifyRepository accountCertifyRepository;

    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long signUp(SignUpRequestDto signUpRequestDto) throws Exception {
        boolean existsByEmail = accountRepository.existsByEmail(signUpRequestDto.getEmail());
        if (existsByEmail) {
            throw new AccountDuplicateException("이미 존재하는 이메일 입니다.");
        }

        boolean existsByNickname = accountRepository.existsByNickname(signUpRequestDto.getNickname());
        if (existsByNickname) {
            throw new AccountDuplicateException("이미 존재하는 닉네임 입니다.");
        }

        Account account = accountRepository.save(signUpRequestDto.toEntity(encoder.encode(signUpRequestDto.getPwd())));

        accountCertifyRepository.save(new AccountCertify(account));

        return account.getId();
    }

    @Transactional
    public Account confirmCertify(String code) {
        AccountCertify accountCertify = accountCertifyRepository.findByCode(code, LocalDateTime.now()).orElseThrow(AccountCertifyNotFoundException::new);
        accountCertify.used();

        return accountCertify.getAccount();
    }

    @Transactional
    public void completeSignup(Account account) {
        account.completeSignup();
    }

    @Transactional
    public void findPwd(Long accountId, FindPwdRequestDto findPwdRequestDto) {
        Account account = accountRepository.findById(accountId).orElseThrow(FindPwdAccountIdNotFoundException::new);
        account.changePwd(encoder.encode(findPwdRequestDto.getPwd()));
    }
}
