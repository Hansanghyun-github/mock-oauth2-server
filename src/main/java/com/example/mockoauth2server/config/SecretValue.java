package com.example.mockoauth2server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecretValue {
    @Value("${client-id}")
    public String clientId;

    @Value("${client-secret}")
    public String clientSecret;

    @Value("${scope}")
    public String scope;
}
