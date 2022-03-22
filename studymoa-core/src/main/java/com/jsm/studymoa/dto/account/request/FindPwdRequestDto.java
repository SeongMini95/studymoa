package com.jsm.studymoa.dto.account.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FindPwdRequestDto {

    private String pwd;

    @Builder
    public FindPwdRequestDto(String pwd) {
        this.pwd = pwd;
    }
}
