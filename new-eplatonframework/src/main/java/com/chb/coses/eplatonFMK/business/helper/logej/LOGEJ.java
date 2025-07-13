package com.chb.coses.eplatonFMK.business.helper.logej;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.net.*;

import com.chb.coses.eplatonFMK.business.helper.CommonUtil;
import com.chb.coses.eplatonFMK.business.helper.FILEapi;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * LOGEJ - Log File Writing
 * Spring 기반으로 전환된 로깅 클래스
 * 
 * @version : 1.0
 * @author : 장 우 승
 */
@Component
public class LOGEJ {

    private static final Logger logger = LoggerFactory.getLogger(LOGEJ.class);

    public static String hostname = CommonUtil.GetHostName();
    public static String Logej_Level_Info_Filename = "/home/coses/env/logej.properties";

    /**
     * 기본 생성자
     */
    public LOGEJ() {
        // Spring에서는 기본 구현
    }

    /**
     * 환경 변수 값 가져오기
     */
    public synchronized String GetEnvValue(String tmp) {
        String ConfigPath = null;
        Properties p = new Properties();
        String tmpv = null;

        try {
            p.load(new FileInputStream(Logej_Level_Info_Filename));
            tmpv = p.getProperty(tmp);
        } catch (IOException e) {
            logger.error("GetEnvValue() 에서 환경셋팅시 예외발생", e);
        }
        return tmpv;
    }

    /**
     * 로그 출력 (시스템명, 이벤트번호, 키, 내용)
     */
    public synchronized void printf(String systemname, String eventno, String key, String contentToWrite) {
        String LoggingFileName = "/home/coses/log/outlog/" + systemname + "." +
                CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();

        FileOutputStream fos = null;
        PrintStream ps = null;

        try {
            fos = new FileOutputStream(LoggingFileName, true);
            ps = new PrintStream(fos);
            contentToWrite = CommonUtil.GetSysTime().substring(0, 8) +
                    "-" + eventno +
                    "-" + key + "|" + contentToWrite;

            ps.println(contentToWrite);
            ps.flush();
        } catch (Exception e) {
            logger.error("printf() 에러", e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (fos != null)
                    fos.close();
            } catch (Exception ex) {
                logger.error("리소스 정리 에러", ex);
            }
        }
    }

    /**
     * 로그 출력 (EPlatonEvent, 내용)
     */
    public synchronized void printf(EPlatonEvent event, String contentToWrite) {
        if (((EPlatonEvent) event).getTPSVCINFODTO().getSystem_name() == null) {
            ((EPlatonEvent) event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
        }
        if (((EPlatonEvent) event).getTPSVCINFODTO().getOrgseq() == null) {
            ((EPlatonEvent) event).getTPSVCINFODTO().setOrgseq("********");
        }

        String LoggingFileName = "/home/coses/log/outlog/" + ((EPlatonEvent) event).getTPSVCINFODTO().getSystem_name()
                + "." +
                CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
        FileOutputStream fos = null;
        PrintStream ps = null;

        try {
            fos = new FileOutputStream(LoggingFileName, true);
            ps = new PrintStream(fos);
            contentToWrite = CommonUtil.GetSysTime().substring(0, 8) +
                    "-" + ((EPlatonEvent) event).getCommon().getEventNo() +
                    "-" + ((EPlatonEvent) event).getTPSVCINFODTO().getOrgseq() + "|" + contentToWrite;
            ps.println(contentToWrite);
            ps.flush();
        } catch (Exception e) {
            logger.error("printf() 에러", e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (fos != null)
                    fos.close();
            } catch (Exception ex) {
                logger.error("리소스 정리 에러", ex);
            }
        }
    }

    /**
     * 예외 로그 출력 (시스템명, 키, 예외)
     */
    public synchronized void eprintf(String systemname, String key, Exception pex) {
        String thrname = Thread.currentThread().getName();
        String LoggingFileName = "Logejb" + "." + CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();

        FileOutputStream fos = null;
        PrintStream ps = null;

        try {
            fos = new FileOutputStream(LoggingFileName, true);
            ps = new PrintStream(fos);
            String contentToWrite = CommonUtil.GetSysTime().substring(0, 8) +
                    "-" + thrname +
                    "-" + key + "|" + pex.toString();
            ps.println(contentToWrite);
            ps.flush();
        } catch (Exception e) {
            logger.error("eprintf() 에러", e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (fos != null)
                    fos.close();
            } catch (Exception ex) {
                logger.error("리소스 정리 에러", ex);
            }
        }
    }

    /**
     * 예외 로그 출력 (EPlatonEvent, 예외)
     */
    public synchronized void eprintf(EPlatonEvent event, Exception pex) {
        if (((EPlatonEvent) event).getTPSVCINFODTO().getSystem_name() == null) {
            ((EPlatonEvent) event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
        }
        if (((EPlatonEvent) event).getTPSVCINFODTO().getOrgseq() == null) {
            ((EPlatonEvent) event).getTPSVCINFODTO().setOrgseq("********");
        }

        String thrname = Thread.currentThread().getName();
        String LoggingFileName = "/home/coses/log/outlog/" + ((EPlatonEvent) event).getTPSVCINFODTO().getSystem_name()
                + "." +
                CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();

        FileOutputStream fos = null;
        PrintStream ps = null;

        try {
            fos = new FileOutputStream(LoggingFileName, true);
            ps = new PrintStream(fos);
            String contentToWrite = CommonUtil.GetSysTime().substring(0, 8) +
                    "-" + ((EPlatonEvent) event).getCommon().getEventNo() +
                    "-" + ((EPlatonEvent) event).getTPSVCINFODTO().getOrgseq() + "|" + pex.toString();
            ps.println(contentToWrite);
            ps.flush();
        } catch (Exception e) {
            logger.error("eprintf() 에러", e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (fos != null)
                    fos.close();
            } catch (Exception ex) {
                logger.error("리소스 정리 에러", ex);
            }
        }
    }
}