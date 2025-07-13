package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * FILE API
 * Spring 기반으로 전환된 파일 API 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class FILEapi {

    private static final Logger logger = LoggerFactory.getLogger(FILEapi.class);

    /**
     * 파일 마지막 수정 시간 가져오기
     */
    public static long FILElastmodified(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                return file.lastModified();
            }
        } catch (Exception e) {
            logger.error("FILElastmodified() 에러", e);
        }
        return 0L;
    }

    /**
     * 파일 읽기
     */
    public static String readFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                return null;
            }

            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();

        } catch (Exception e) {
            logger.error("readFile() 에러", e);
            return null;
        }
    }

    /**
     * 파일 쓰기
     */
    public static boolean writeFile(String filename, String content) {
        try {
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            return true;
        } catch (Exception e) {
            logger.error("writeFile() 에러", e);
            return false;
        }
    }

    /**
     * 파일 복사
     */
    public static boolean copyFile(String sourceFile, String targetFile) {
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fis.close();
            fos.close();
            return true;
        } catch (Exception e) {
            logger.error("copyFile() 에러", e);
            return false;
        }
    }

    /**
     * 파일 삭제
     */
    public static boolean deleteFile(String filename) {
        try {
            File file = new File(filename);
            return file.delete();
        } catch (Exception e) {
            logger.error("deleteFile() 에러", e);
            return false;
        }
    }

    /**
     * 파일 존재 여부 확인
     */
    public static boolean fileExists(String filename) {
        try {
            File file = new File(filename);
            return file.exists();
        } catch (Exception e) {
            logger.error("fileExists() 에러", e);
            return false;
        }
    }

    /**
     * 디렉토리 생성
     */
    public static boolean createDirectory(String dirname) {
        try {
            File dir = new File(dirname);
            return dir.mkdirs();
        } catch (Exception e) {
            logger.error("createDirectory() 에러", e);
            return false;
        }
    }

    /**
     * 파일 크기 가져오기
     */
    public static long getFileSize(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                return file.length();
            }
        } catch (Exception e) {
            logger.error("getFileSize() 에러", e);
        }
        return 0L;
    }
}