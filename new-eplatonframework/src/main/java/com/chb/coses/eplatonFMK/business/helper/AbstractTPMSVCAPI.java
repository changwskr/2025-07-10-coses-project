package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;

/**
 * Abstract TPM Service API
 * Spring 기반으로 전환된 TPM 서비스 API 추상 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public abstract class AbstractTPMSVCAPI {

    protected String url;

    /**
     * 기본 생성자
     */
    public AbstractTPMSVCAPI() {
        // Spring에서는 기본 구현
    }

    /**
     * URL 설정
     * 
     * @param url URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * URL 가져오기
     * 
     * @return URL
     */
    public String getUrl() {
        return url;
    }
}