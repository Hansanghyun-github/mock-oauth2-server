package com.example.temporaryoauth2server.controller;

import com.example.temporaryoauth2server.service.OAuth2Service;
import com.example.temporaryoauth2server.service.dto.TokenResponse;
import com.example.temporaryoauth2server.service.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {
    private final OAuth2Service oAuth2Service;

    @GetMapping("/oauth2/authorize")
    public void authorize(
            @RequestParam(value = "response_type") String response_type,
            @RequestParam(value = "client_id") String client_id,
            @RequestParam(value = "scope") String scope,
            @RequestParam(value = "redirect_uri") String redirect_uri,
            @RequestParam(value = "state") String state,
            HttpServletResponse response
    ) throws IOException {
        String authorizeCode = oAuth2Service.authorize(response_type, client_id, scope);
        log.info("authorizeCode: " + authorizeCode);
        response.sendRedirect(redirect_uri + "?code=" + authorizeCode + "&state=" + state);
    }

    @PostMapping("/oauth2/token")
    public TokenResponse token(
            @RequestParam(value = "redirect_uri") String redirectUri,
            @RequestParam(value = "code") String code,
            @RequestParam(value = "grant_type") String grantType
    ) {
        log.info("accessToken");
        return oAuth2Service.getToken(redirectUri, code, grantType);
    }

    @GetMapping("/oauth2/userinfo")
    public UserResponse userinfo(
            @RequestHeader(value = "Authorization") String authorization
    ) {
        log.info("authorization: " + authorization);

        authorization = authorization.replace("Bearer ", "");
        if(!authorization.equals("access_token"))
            throw new IllegalArgumentException("Invalid access_token");

        return new UserResponse("mock@email", "fsdf564wq89fDSF", "sub");
    }
}
