package com.skcc.oversea.framework.transaction.tpmutil;

import org.springframework.stereotype.Component;

@Component
public class TPMUtil {

    public void initializeTPM() {
        // TPM 초기화 로직
    }

    public void cleanupTPM() {
        // TPM 정리 로직
    }

    public boolean isTPMAvailable() {
        // TPM 사용 가능 여부 확인
        return true;
    }
}