package com.example.mockoauth2server.controller;

import com.example.mockoauth2server.service.OAuth2Service;
import com.example.mockoauth2server.service.dto.TokenResponse;
import com.example.mockoauth2server.service.dto.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {
    private final OAuth2Service oAuth2Service;

    @GetMapping("/oauth2/authorize")
    public void authorize(
            @RequestParam(value = "response_type") @NotBlank String response_type,
            @RequestParam(value = "client_id") @NotBlank String client_id,
            @RequestParam(value = "scope") @NotBlank String scope,
            @RequestParam(value = "redirect_uri") @NotBlank String redirect_uri,
            @RequestParam(value = "state") @NotBlank String state,
            @RequestParam(value = "code") @NotBlank String code,
            HttpServletResponse response
    ) throws IOException {
        String authorizeCode = oAuth2Service.authorize(response_type, client_id, scope, code);
        log.info("authorizeCode: " + authorizeCode);
        response.sendRedirect(redirect_uri + "?code=" + authorizeCode + "&state=" + state);
    }

    @PostMapping("/oauth2/token")
    public TokenResponse token(
            @RequestParam(value = "redirect_uri") @NotBlank String redirectUri,
            @RequestParam(value = "code") @NotBlank String code,
            @RequestParam(value = "grant_type") @NotBlank String grantType
    ) {
        log.info("accessToken");
        return oAuth2Service.getToken(redirectUri, code, grantType);
    }

    @GetMapping("/oauth2/userinfo")
    public UserResponse userinfo(
            @RequestHeader(value = "Authorization") @NotBlank String authorization
    ) {
        log.info("authorization: " + authorization);

        authorization = authorization.replace("Bearer ", "");
        if(!authorization.equals("access_token"))
            throw new IllegalArgumentException("Invalid access_token");

        return oAuth2Service.getUserInfo(authorization);
    }
}
