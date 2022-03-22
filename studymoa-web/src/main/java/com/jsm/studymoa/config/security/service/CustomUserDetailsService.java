package com.jsm.studymoa.config.security.service;

import com.jsm.studymoa.config.security.dto.SessionAccount;
import com.jsm.studymoa.domain.account.Account;
import com.jsm.studymoa.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final HttpSession httpSession;
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));

        httpSession.setAttribute("account", new SessionAccount(account));

        return new User(username, account.getPwd(), Collections.singleton(new SimpleGrantedAuthority(account.getRole().name())));
    }
}
