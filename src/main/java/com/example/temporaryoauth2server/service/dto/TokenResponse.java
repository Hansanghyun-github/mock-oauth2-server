package com.example.temporaryoauth2server.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TokenResponse {
    private final String access_token;
    private final String token_type;
    private final Long expires_in;
    private final String scope = "account_email";

    public TokenResponse(String access_token) {
        this.access_token = access_token;
        this.token_type = "bearer";
        this.expires_in = 300L;
    }
}
