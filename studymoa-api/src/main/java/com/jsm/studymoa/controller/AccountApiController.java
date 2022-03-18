package com.jsm.studymoa.controller;

import com.jsm.studymoa.dto.account.request.SignUpRequestDto;
import com.jsm.studymoa.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountService accountService;

    @PostMapping
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws Exception {
        accountService.signUp(signUpRequestDto);

        // TODO: 2022-03-17 인증 메일 코드 해야 됨
    }
}
