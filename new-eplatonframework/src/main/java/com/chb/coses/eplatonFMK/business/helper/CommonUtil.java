package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.Date;

/**
 * CommonUtil - 공통 유틸리티 클래스
 * Spring 기반으로 전환된 공통 유틸리티
 * 
 * @version : 1.0
 * @author : 장 우 승
 */
@Component
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 문자열 치환
     * 
     * @param str     바꾸려는 문자열을 가진 원본
     * @param pattern 찾을 문자열
     * @param replace 바꿔줄 문자열
     * @return 치환된 문자열
     */
    public static String replace(String str, String pattern, String replace) {
        int s = 0; // 찾기 시작할 위치
        int e = 0; // StringBuffer에 append 할 위치
        StringBuffer result = new StringBuffer(); // 잠시 문자열 담궈둘 놈

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

    /**
     * 정수 체크
     */
    public static final boolean GetIntChk(String string, int piStrLen) {
        for (int index = 0; index < piStrLen; index++) {
            if (string.charAt(index) < 48 || string.charAt(index) > 57) {
                return false;
            }
        }
        return true;
    }

    /**
     * 런타임 명령 실행
     */
    public static final boolean runtime(String cmd) {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("/home/cosif/jtm/bin/jmboot " + cmd);
        } catch (IOException ex) {
            logger.error("runtime() 예외발생", ex);
            return false;
        }
        return true;
    }

    /**
     * 시간 간격 계산 (초)
     */
    public int Interval_GetTimeSec(String psStartTimer, String psEndTimer) {
        String ss = null;
        String mm = null;
        String ee = null;
        int startsec = 99999999;
        int endsec = 99999999;

        // 시작타임
        ss = psStartTimer.substring(0, 2);
        mm = psStartTimer.substring(2, 4);
        ee = psStartTimer.substring(4, 6);
        startsec = Str2Int(ss) * 60 * 60 + Str2Int(mm) * 60 + Str2Int(ee);

        // 종료타임
        ss = psEndTimer.substring(0, 2);
        mm = psEndTimer.substring(2, 4);
        ee = psEndTimer.substring(4, 6);
        endsec = Str2Int(ss) * 60 * 60 + Str2Int(mm) * 60 + Str2Int(ee);

        return (endsec - startsec);
    }

    /**
     * System.out.println으로 화면에 보여준다
     */
    public static final void systemOut(String msg) {
        System.out.println(msg);
    }

    /**
     * len 보다 긴 문자열일 경우 자른다.
     */
    public static final String cutString(String str, int len) {
        if (str != null) {
            if (str.length() < len + 1) {
                return str;
            } else {
                return str.substring(0, len);
            }
        }
        return "";
    }

    /**
     * 문자열 자르기 (시작위치, 길이)
     */
    public static final String cutString(String str, int startpo, int len) {
        if (str != null) {
            return str.substring(startpo, (len + 1));
        }
        return "";
    }

    /**
     * KSC5601으로 변환한다.
     */
    public static final String ascToksc(String str) throws UnsupportedEncodingException {
        if (str == null)
            return "";
        return new String(str.getBytes("8859_1"), "KSC5601");
    }

    /**
     * 8859_1로 변환한다.
     */
    public static final String kscToasc(String str) throws UnsupportedEncodingException {
        if (str == null)
            return "";
        return new String(str.getBytes("KSC5601"), "8859_1");
    }

    /**
     * 입력된 String이 한글, 알파벳, 숫자인지를 체크한다.
     * 0인 경우 숫자, 1인 경우 알파벳, 2인 경우 한글
     */
    public static final int checkHan(String data) {
        int code = (int) data.charAt(0);
        int result = 1; // 알파벳인 경우
        if (code > 127) {
            result = 2; // 한글인 경우
        } else if (code >= 48 && code <= 57) {
            result = 0; // 숫자인 경우
        }
        return result;
    }

    /**
     * 주어진 스트링이 한글인지 판단
     */
    public static final boolean isHanGul(String str) {
        if (str == null)
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > 127) {
                return true;
            }
        }
        return false;
    }

    /**
     * null 체크
     */
    public static final String nullCheckString(String str) {
        if (str == null)
            return "";
        return str;
    }

    /**
     * 시간 포맷팅
     */
    public static final String getTime(long time) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
    }

    /**
     * 시간 포맷팅 (메시지 포함)
     */
    public static final void getTime(long time, String msg) {
        System.out.println(getTime(time) + " " + msg);
    }

    /**
     * 현재 시간 가져오기
     */
    public static String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * 자유 포맷 시간 가져오기
     */
    public static String getTimeFreeFormat(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * String to Int 변환
     */
    public static final int STR2INT(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * String to Int 변환
     */
    public static final int Str2Int(String str) {
        return STR2INT(str);
    }

    /**
     * String to Int 변환 (atoi)
     */
    public static final int atoi(String str) {
        return STR2INT(str);
    }

    /**
     * String to Long 변환
     */
    public static final long Str2Long(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * String to Float 변환
     */
    public static final float Str2Float(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    /**
     * String to Double 변환
     */
    public static final double Str2Double(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Int to String 변환
     */
    public static final String Int2Str(int n) {
        return String.valueOf(n);
    }

    /**
     * Float to String 변환
     */
    public static final String Float2Str(float n) {
        return String.valueOf(n);
    }

    /**
     * Long to String 변환
     */
    public static final String Long2Str(long l) {
        return String.valueOf(l);
    }

    /**
     * Double to String 변환
     */
    public static final String Double2Str(double d) {
        return String.valueOf(d);
    }

    /**
     * 대문자 변환
     */
    public static final String TOupper(String str) {
        if (str == null)
            return "";
        return str.toUpperCase();
    }

    /**
     * 소문자 변환
     */
    public static final String TOlower(String str) {
        if (str == null)
            return "";
        return str.toLowerCase();
    }

    /**
     * Char to String 변환
     */
    public static final String Char2Str(char ch) {
        return String.valueOf(ch);
    }

    /**
     * 문자열 포함 여부 체크
     */
    public static final boolean exist_str(String org, String tar) {
        if (org == null || tar == null)
            return false;
        return org.indexOf(tar) >= 0;
    }

    /**
     * 문자열 포함 여부 체크
     */
    public static final boolean check_contain_string(String org, String tar) {
        return exist_str(org, tar);
    }

    /**
     * 문자열 비교
     */
    public static final boolean memcmp(String st1, String st2) {
        if (st1 == null && st2 == null)
            return true;
        if (st1 == null || st2 == null)
            return false;
        return st1.equals(st2);
    }

    /**
     * 문자열 비교 (길이 지정)
     */
    public static final boolean memcmp(String st1, String st2, int len) {
        if (st1 == null && st2 == null)
            return true;
        if (st1 == null || st2 == null)
            return false;

        int minLen = Math.min(st1.length(), st2.length());
        minLen = Math.min(minLen, len);

        return st1.substring(0, minLen).equals(st2.substring(0, minLen));
    }

    /**
     * 문자열 복사
     */
    public static final String memcpy(String org, int len) {
        if (org == null)
            return "";
        if (org.length() <= len)
            return org;
        return org.substring(0, len);
    }

    /**
     * 파일 정보 가져오기
     */
    public static final int FileInfo(String filepath) {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                return (int) file.length();
            }
        } catch (Exception e) {
            logger.error("FileInfo() 에러", e);
        }
        return 0;
    }

    /**
     * 파일 정보 출력
     */
    public static final void printOne(File f) {
        if (f.exists()) {
            System.out.println("File: " + f.getName() + ", Size: " + f.length());
        }
    }

    /**
     * 파일 복사
     */
    public static final int cpFile2File(String iname, String oname) {
        try {
            FileInputStream fis = new FileInputStream(iname);
            FileOutputStream fos = new FileOutputStream(oname);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fis.close();
            fos.close();
            return 1;
        } catch (Exception e) {
            logger.error("cpFile2File() 에러", e);
            return 0;
        }
    }

    /**
     * 디렉토리 삭제
     */
    public static final void deleteDIR(String args) throws Exception {
        File dir = new File(args);
        if (dir.exists()) {
            deleteAll(dir);
        }
    }

    /**
     * 모든 파일 삭제
     */
    public static final void deleteAll(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteAll(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }

    /**
     * 파일에 내용 추가
     */
    public static final int appendFileWrite(String path2filename, String writebuff) {
        try {
            FileWriter fw = new FileWriter(path2filename, true);
            fw.write(writebuff);
            fw.close();
            return 1;
        } catch (Exception e) {
            logger.error("appendFileWrite() 에러", e);
            return 0;
        }
    }

    /**
     * 로컬 호스트명 가져오기
     */
    public static final String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            logger.error("getLocalHostName() 에러", e);
            return "localhost";
        }
    }

    /**
     * 호스트명 가져오기
     */
    public static final String GetHostName() {
        return getLocalHostName();
    }

    /**
     * 시스템 시간 가져오기
     */
    public static final String GetSysTime() {
        return new SimpleDateFormat("HHmmss").format(new Date());
    }

    /**
     * 시스템 날짜 가져오기
     */
    public static final String GetSysDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 현재 날짜 가져오기
     */
    public static final String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 시간 간격 계산
     */
    public static final int ItvSec(String psStartTimer, String psEndTimer) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
            Date startDate = sdf.parse(psStartTimer);
            Date endDate = sdf.parse(psEndTimer);
            return (int) ((endDate.getTime() - startDate.getTime()) / 1000);
        } catch (Exception e) {
            logger.error("ItvSec() 에러", e);
            return 0;
        }
    }

    /**
     * 영어로 변환
     */
    public static final String en(String ko) {
        // 실제 구현에서는 한영 변환 로직 필요
        return ko;
    }

    /**
     * 한글로 변환
     */
    public static final String ko(String en) {
        // 실제 구현에서는 영한 변환 로직 필요
        return en;
    }

    /**
     * 명령어 실행
     */
    public static final int exec_com(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            return process.waitFor();
        } catch (Exception e) {
            logger.error("exec_com() 에러", e);
            return -1;
        }
    }

    /**
     * 명령어 실행 (배열)
     */
    public static final int execcommand(String args) {
        try {
            Process process = Runtime.getRuntime().exec(args);
            return process.waitFor();
        } catch (Exception e) {
            logger.error("execcommand() 에러", e);
            return -1;
        }
    }

    /**
     * DB 연결 가져오기
     */
    public static final Connection getDBConnection() {
        // Spring에서는 DataSource를 주입받아 사용
        return null;
    }

    /**
     * DB 연결 가져오기 (사용자명, 비밀번호)
     */
    public static final Connection getDBConnection(String usr, String password) {
        // Spring에서는 DataSource를 주입받아 사용
        return null;
    }

    /**
     * 호스트 시퀀스 가져오기
     */
    public static final String GetHostSeq() {
        // 실제 구현에서는 호스트 시퀀스 로직 필요
        return "001";
    }

    /**
     * 호스트 시퀀스 가져오기 (DB 연결)
     */
    public static final String gethostseq(Connection conn) {
        // 실제 구현에서는 DB에서 호스트 시퀀스 조회 로직 필요
        return "001";
    }

    /**
     * 로그 기록
     */
    public static final boolean log(String contentToWrite) {
        try {
            String logFile = "/home/coses/log/outlog/" + GetHostName() + "." + GetSysDate();
            appendFileWrite(logFile, getCurrentDate() + " " + contentToWrite + "\n");
            return true;
        } catch (Exception e) {
            logger.error("log() 에러", e);
            return false;
        }
    }

    /**
     * 프로퍼티 가져오기
     */
    public static final boolean getProperties() {
        // 실제 구현에서는 프로퍼티 로딩 로직 필요
        return true;
    }

    /**
     * 정수 배열 정렬
     */
    public static final int[] SortINTarray(int num[]) {
        Arrays.sort(num);
        return num;
    }

    /**
     * 소수점 포맷팅
     */
    public static final double cutDecimalFormat(String pattern, double num) {
        try {
            DecimalFormat df = new DecimalFormat(pattern);
            return Double.parseDouble(df.format(num));
        } catch (Exception e) {
            logger.error("cutDecimalFormat() 에러", e);
            return num;
        }
    }

    /**
     * 8자리 0 패딩
     */
    public static final String ZeroToStr8(String str) {
        return ZeroToStr(str, 8);
    }

    /**
     * 0 패딩
     */
    public static final String ZeroToStr(String str, int size) {
        if (str == null)
            str = "";
        while (str.length() < size) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * 왼쪽 0 패딩
     */
    public static final String LZeroToStr(String str, int size) {
        return ZeroToStr(str, size);
    }

    /**
     * 큰 0 패딩
     */
    public static final String BigZeroToStr(String str, int size) {
        if (str == null)
            str = "";
        while (str.length() < size) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * 공백 제거
     */
    public static final String CutSpaceToStr(String str, int size) {
        if (str == null)
            str = "";
        str = str.trim();
        if (str.length() > size) {
            str = str.substring(0, size);
        }
        return str;
    }

    /**
     * 공백 패딩
     */
    public static final String SpaceToStr(String str, int size) {
        if (str == null)
            str = "";
        while (str.length() < size) {
            str = str + " ";
        }
        return str;
    }

    /**
     * 별표 패딩
     */
    public static final String StarToStr(String str, int size) {
        if (str == null)
            str = "";
        while (str.length() < size) {
            str = str + "*";
        }
        return str;
    }

    /**
     * 문자열을 별표로 변환
     */
    public static final String ConvertStrToStar(String str, int size) {
        if (str == null)
            str = "";
        String result = "";
        for (int i = 0; i < Math.min(str.length(), size); i++) {
            result += "*";
        }
        return result;
    }

    /**
     * 앞쪽 공백 패딩
     */
    public static final String PSpaceToStr(String str1, int size) {
        if (str1 == null)
            str1 = "";
        while (str1.length() < size) {
            str1 = " " + str1;
        }
        return str1;
    }

    /**
     * 뒤쪽 공백 패딩
     */
    public static final String AtferSpaceToStr(String str1, int size) {
        return SpaceToStr(str1, size);
    }

    /**
     * 공백 채우기
     */
    public static final String FillSpaceToStr(String str1, int size) {
        if (str1 == null)
            str1 = "";
        while (str1.length() < size) {
            str1 = str1 + " ";
        }
        return str1;
    }

    /**
     * 8자리 0 패딩 (int)
     */
    public static final String ZeroToStr8(int str) {
        return ZeroToStr8(String.valueOf(str));
    }

    /**
     * 8자리 0 패딩 (long)
     */
    public static final String ZeroToStr8(long str) {
        return ZeroToStr8(String.valueOf(str));
    }

    /**
     * 랜덤 숫자 생성
     */
    public static final int getRandomNum(int k) {
        Random random = new Random();
        return random.nextInt(k);
    }
}