package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.transfer.TPSVCINFODTO;
import com.chb.coses.eplatonFMK.transfer.EPlatonCommonDTO;

import java.util.*;

/**
 * TPM Utility
 * Spring 기반으로 전환된 TPM 유틸리티 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class TPMUtil {

    private static final Logger logger = LoggerFactory.getLogger(TPMUtil.class);

    /**
     * TPM 서비스 정보 설정
     */
    public static void setTPMServiceInfo(EPlatonEvent event, String systemName, String operationName,
            String actionName) {
        try {
            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            tpsvcinfo.setSystem_name(systemName);
            tpsvcinfo.setOperation_name(operationName);
            tpsvcinfo.setAction_name(actionName);
            tpsvcinfo.setTpfq("100");

            logger.debug("TPM 서비스 정보 설정 - System: {}, Operation: {}, Action: {}",
                    systemName, operationName, actionName);
        } catch (Exception e) {
            logger.error("setTPMServiceInfo() 에러", e);
        }
    }

    /**
     * TPM 공통 정보 설정
     */
    public static void setTPMCommonInfo(EPlatonEvent event) {
        try {
            EPlatonCommonDTO common = event.getCommon();
            common.setTimeZone("GMT+09:00");
            common.setFxRateCount(1);
            common.setSystemDate(CommonUtil.GetSysDate());
            common.setSystemInTime(CommonUtil.GetSysTime());

            logger.debug("TPM 공통 정보 설정");
        } catch (Exception e) {
            logger.error("setTPMCommonInfo() 에러", e);
        }
    }

    /**
     * TPM 에러 정보 설정
     */
    public static void setTPMErrorInfo(EPlatonEvent event, String errorCode, String errorMessage) {
        try {
            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            tpsvcinfo.setErrorcode(errorCode);
            tpsvcinfo.setError_message(errorMessage);

            logger.error("TPM 에러 정보 설정 - Code: {}, Message: {}", errorCode, errorMessage);
        } catch (Exception e) {
            logger.error("setTPMErrorInfo() 에러", e);
        }
    }

    /**
     * TPM 성공 정보 설정
     */
    public static void setTPMSuccessInfo(EPlatonEvent event) {
        try {
            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            tpsvcinfo.setErrorcode("0000");
            tpsvcinfo.setError_message("정상 처리되었습니다.");

            logger.debug("TPM 성공 정보 설정");
        } catch (Exception e) {
            logger.error("setTPMSuccessInfo() 에러", e);
        }
    }

    /**
     * TPM 이벤트 검증
     */
    public static boolean validateTPMEvent(EPlatonEvent event) {
        try {
            if (event == null) {
                logger.error("TPM 이벤트가 null입니다.");
                return false;
            }

            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            EPlatonCommonDTO common = event.getCommon();

            if (tpsvcinfo == null) {
                logger.error("TPSVCINFODTO가 null입니다.");
                return false;
            }

            if (common == null) {
                logger.error("EPlatonCommonDTO가 null입니다.");
                return false;
            }

            if (tpsvcinfo.getSystem_name() == null || tpsvcinfo.getSystem_name().trim().isEmpty()) {
                logger.error("시스템명이 설정되지 않았습니다.");
                return false;
            }

            if (tpsvcinfo.getOperation_name() == null || tpsvcinfo.getOperation_name().trim().isEmpty()) {
                logger.error("오퍼레이션명이 설정되지 않았습니다.");
                return false;
            }

            logger.debug("TPM 이벤트 검증 성공");
            return true;

        } catch (Exception e) {
            logger.error("validateTPMEvent() 에러", e);
            return false;
        }
    }

    /**
     * TPM 이벤트 로깅
     */
    public static void logTPMEvent(EPlatonEvent event, String message) {
        try {
            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            EPlatonCommonDTO common = event.getCommon();

            logger.info("TPM 이벤트 로그 - System: {}, Operation: {}, EventNo: {}, Message: {}",
                    tpsvcinfo.getSystem_name(), tpsvcinfo.getOperation_name(),
                    common.getEventNo(), message);
        } catch (Exception e) {
            logger.error("logTPMEvent() 에러", e);
        }
    }

    /**
     * TPM 이벤트 에러 로깅
     */
    public static void logTPMError(EPlatonEvent event, Exception e) {
        try {
            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            EPlatonCommonDTO common = event.getCommon();

            logger.error("TPM 이벤트 에러 - System: {}, Operation: {}, EventNo: {}",
                    tpsvcinfo.getSystem_name(), tpsvcinfo.getOperation_name(),
                    common.getEventNo(), e);
        } catch (Exception ex) {
            logger.error("logTPMError() 에러", ex);
        }
    }

    /**
     * TPM 응답 시간 설정
     */
    public static void setTPMResponseTime(EPlatonEvent event) {
        try {
            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            tpsvcinfo.setSystemOutTime(CommonUtil.GetSysTime());

            logger.debug("TPM 응답 시간 설정");
        } catch (Exception e) {
            logger.error("setTPMResponseTime() 에러", e);
        }
    }

    /**
     * TPM 처리 시간 계산
     */
    public static int calculateTPMProcessingTime(EPlatonEvent event) {
        try {
            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            String inTime = tpsvcinfo.getSystemInTime();
            String outTime = tpsvcinfo.getSystemOutTime();

            if (inTime != null && outTime != null) {
                return CommonUtil.Interval_GetTimeSec(inTime, outTime);
            }

            return 0;
        } catch (Exception e) {
            logger.error("calculateTPMProcessingTime() 에러", e);
            return 0;
        }
    }

    /**
     * TPM 이벤트 복사
     */
    public static EPlatonEvent copyTPMEvent(EPlatonEvent original) {
        try {
            EPlatonEvent copy = new EPlatonEvent();

            // 공통 정보 복사
            EPlatonCommonDTO originalCommon = original.getCommon();
            EPlatonCommonDTO copyCommon = copy.getCommon();
            copyCommon.setTerminalID(originalCommon.getTerminalID());
            copyCommon.setTerminalType(originalCommon.getTerminalType());
            copyCommon.setBankCode(originalCommon.getBankCode());
            copyCommon.setBranchCode(originalCommon.getBranchCode());
            copyCommon.setUserID(originalCommon.getUserID());
            copyCommon.setEventNo(originalCommon.getEventNo());
            copyCommon.setTimeZone(originalCommon.getTimeZone());
            copyCommon.setSystemDate(originalCommon.getSystemDate());

            // TPM 서비스 정보 복사
            TPSVCINFODTO originalTpm = original.getTPSVCINFODTO();
            TPSVCINFODTO copyTpm = copy.getTPSVCINFODTO();
            copyTpm.setSystem_name(originalTpm.getSystem_name());
            copyTpm.setOperation_name(originalTpm.getOperation_name());
            copyTpm.setAction_name(originalTpm.getAction_name());
            copyTpm.setTpfq(originalTpm.getTpfq());
            copyTpm.setErrorcode(originalTpm.getErrorcode());
            copyTpm.setError_message(originalTpm.getError_message());

            // 요청/응답 복사
            copy.setRequest(original.getRequest());
            copy.setResponse(original.getResponse());

            logger.debug("TPM 이벤트 복사 완료");
            return copy;

        } catch (Exception e) {
            logger.error("copyTPMEvent() 에러", e);
            return null;
        }
    }
}