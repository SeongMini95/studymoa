package com.jsm.studymoa.controller;

import com.jsm.studymoa.config.exception.FindPwdAccountIdNotFoundException;
import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.dto.account.request.FindPwdRequestDto;
import com.jsm.studymoa.dto.account.request.SignUpRequestDto;
import com.jsm.studymoa.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountService accountService;
    private final HttpSession httpSession;

    @PostMapping
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws Exception {
        accountService.signUp(signUpRequestDto);

        // TODO: 2022-03-17 인증 메일 코드 해야 됨
    }

    @GetMapping("/certify/{type}")
    public void confirmCertify(@RequestParam String code, @PathVariable String type) {
        Account account = accountService.confirmCertify(code);

        if (type.equals("signUp")) {
            accountService.completeSignup(account);
        } else if (type.equals("findPwd")) {
            httpSession.setAttribute("findPwdAccountId", account.getId());
        }
    }

    @PostMapping("/findPwd")
    public void findPwd(@RequestBody FindPwdRequestDto findPwdRequestDto) {
        Long accountId = (Long) httpSession.getAttribute("findPwdAccountId");
        if (accountId == null) {
            throw new FindPwdAccountIdNotFoundException();
        }

        accountService.findPwd(accountId, findPwdRequestDto);

        httpSession.removeAttribute("findPwdAccountId");
    }
}
