package com.jsm.studymoa.domain.account.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Role {

    GUEST("손님", "1"),
    USER("사용자", "2"),
    ADMIN("관리자", "3");

    private final String desc;
    private final String code;

    public static Role ofCode(String code) {
        return Arrays.stream(Role.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Role에 code=[%s]가 존재하지 않습니다.", code)));
    }
}
