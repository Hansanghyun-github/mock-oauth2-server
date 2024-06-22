package com.example.temporaryoauth2server.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final String email;
    private final String id;
    private final String sub;
}
