package com.example.mockoauth2server.service;

import com.example.mockoauth2server.config.CodeManager;
import com.example.mockoauth2server.config.SecretValue;
import com.example.mockoauth2server.service.dto.TokenResponse;
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
            String scope) {
        if(!clientId.equals(secretValue.clientId) || !scope.equals(secretValue.scope))
            throw new IllegalArgumentException("Invalid client_id or scope");
        if (!responseType.equals("code"))
            throw new IllegalArgumentException("Invalid response_type");
        return codeManager.getCode();
    }

    public TokenResponse getToken(
            String redirectUri,
            String code,
            String grantType) {
        if (!grantType.equals("authorization_code"))
            throw new IllegalArgumentException("Invalid grant_type");
        if (!code.equals(codeManager.getCode()))
            throw new IllegalArgumentException("Invalid code");
        return new TokenResponse("access_token");
    }
}
