package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * TPM Service API Interface
 * Spring 기반으로 전환된 TPM 서비스 API 인터페이스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public interface ITPMSVCAPI {

    /**
     * TPS send/receive 처리
     * 
     * @param event EPlaton 이벤트
     * @return 처리된 이벤트
     */
    EPlatonEvent TPSsendrecv(EPlatonEvent event);

    /**
     * TPS send/receive 처리 (파라미터 포함)
     * 
     * @param porgSystem       원본 시스템
     * @param psystemName      시스템 이름
     * @param pactionClassName 액션 클래스 이름
     * @param poperationName   오퍼레이션 이름
     * @param event            EPlaton 이벤트
     * @return 처리된 이벤트
     */
    EPlatonEvent TPSsendrecv(String porgSystem, String psystemName, String pactionClassName, String poperationName,
            EPlatonEvent event);

    /**
     * TPS receive 처리
     * 
     * @param event EPlaton 이벤트
     * @return 처리된 이벤트
     */
    EPlatonEvent TPSrecv(EPlatonEvent event);

    /**
     * TPS send 처리
     * 
     * @param event EPlaton 이벤트
     * @return 처리된 이벤트
     */
    EPlatonEvent TPSsend(EPlatonEvent event);
}