package com.example.mockoauth2server.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CodeManager {
    private final SecretValue secretValue;
    public void validateCode(String code) {
        if (!code.startsWith(secretValue.code))
            throw new IllegalArgumentException("Invalid code");

        // replace input code from secretValue.code to ""
        String s = code.replace(secretValue.code, "");

        // validate s is numeric
        try {
            int l = Integer.parseInt(s);
            if(l < 0 || l > 1000)
                throw new IllegalArgumentException("Invalid code");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid code");
        }
    }

    public int getNumber(String authorization) {
        String s = authorization.replace(secretValue.code, "");
        return Integer.parseInt(s);
    }
}
