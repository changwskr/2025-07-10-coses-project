package com.skcc.oversea.foundation.tpmservice;

import org.springframework.stereotype.Component;

@Component
public class TPSsendrecv {

    public void sendMessage(String message) {
        // 메시지 전송 로직
    }

    public String receiveMessage() {
        // 메시지 수신 로직
        return "";
    }

    public boolean isConnected() {
        // 연결 상태 확인
        return true;
    }
}