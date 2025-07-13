package com.chb.coses.eplatonFMK.business.facade.ecommon;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * Common Management Service Bean
 * Spring 기반으로 전환된 Common 관리 서비스
 * 
 * @author unascribed
 * @version 1.0
 */
@Service
public class CommonManagementSB {

    private static final Logger logger = LoggerFactory.getLogger(CommonManagementSB.class);

    /**
     * Common 비즈니스 로직 실행
     * 
     * @param event EPlaton 이벤트
     * @return 처리 결과 이벤트
     */
    public EPlatonEvent execute(EPlatonEvent event) {
        try {
            logger.info("CommonManagementSB.execute() 시작");

            // Common 관련 비즈니스 로직 구현
            // 예: 공통 코드 조회, 시스템 정보 조회 등

            logger.info("CommonManagementSB.execute() 완료");
            return event;

        } catch (Exception e) {
            logger.error("CommonManagementSB.execute() 에러", e);
            event.getTPSVCINFODTO().setErrorcode("ECOM001");
            event.getTPSVCINFODTO().setError_message("Common 처리 중 오류 발생");
            return event;
        }
    }
}