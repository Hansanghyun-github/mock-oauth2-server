package com.example.temporaryoauth2server.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CodeManager {
    private final String code = "fds64saFDSGSG648645123SGafsw";

    public String getCode() {
        return code;
    }
}
