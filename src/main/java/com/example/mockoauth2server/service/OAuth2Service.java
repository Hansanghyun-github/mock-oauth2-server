package com.example.mockoauth2server.service;

import com.example.mockoauth2server.global.CodeManager;
import com.example.mockoauth2server.global.SecretValue;
import com.example.mockoauth2server.service.dto.TokenResponse;
import com.example.mockoauth2server.service.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2Service {
    private final SecretValue secretValue;
    private final CodeManager codeManager;
    public String authorize(
            String responseType,
            String clientId,
            String scope,
            String code) {
        if(!clientId.equals(secretValue.clientId) || !scope.equals(secretValue.scope))
            throw new IllegalArgumentException("Invalid client_id or scope");
        if (!responseType.equals("code"))
            throw new IllegalArgumentException("Invalid response_type");
        codeManager.validateCode(code);
        return code;
    }

    public TokenResponse getToken(
            String redirectUri,
            String code,
            String grantType) {
        if (!grantType.equals("authorization_code"))
            throw new IllegalArgumentException("Invalid grant_type");
        codeManager.validateCode(code);
        return new TokenResponse(code);
    }

    public UserResponse getUserInfo(String authorization) {
        codeManager.validateCode(authorization);
        int number = codeManager.getNumber(authorization);
        return new UserResponse("mock" + number + "@email", "mock_test_oauth2" + number, "sub");
    }
}
