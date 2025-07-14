package com.skcc.oversea.foundation.security;

import org.springframework.stereotype.Component;

@Component
public class SecurityManager {

    public boolean authenticate(String username, String password) {
        // 인증 로직
        return true;
    }

    public boolean authorize(String username, String resource) {
        // 권한 확인 로직
        return true;
    }

    public String encrypt(String data) {
        // 암호화 로직
        return data;
    }

    public String decrypt(String encryptedData) {
        // 복호화 로직
        return encryptedData;
    }
}