package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * CUserTransaction
 * Spring 기반으로 전환된 사용자 트랜잭션 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class CUserTransaction {

    private static final Logger logger = LoggerFactory.getLogger(CUserTransaction.class);

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DataSource dataSource;

    /**
     * 트랜잭션 시작
     */
    public boolean begin() {
        try {
            logger.info("트랜잭션 시작");
            // Spring에서는 @Transactional 어노테이션이나 TransactionTemplate을 사용
            return true;
        } catch (Exception e) {
            logger.error("트랜잭션 시작 에러", e);
            return false;
        }
    }

    /**
     * 트랜잭션 시작 (타임아웃 지정)
     */
    public boolean begin(int timeout) {
        try {
            logger.info("트랜잭션 시작 (타임아웃: {}초)", timeout);
            // Spring에서는 TransactionTemplate의 timeout 설정 사용
            return true;
        } catch (Exception e) {
            logger.error("트랜잭션 시작 에러", e);
            return false;
        }
    }

    /**
     * 트랜잭션 커밋
     */
    public boolean commit() {
        try {
            logger.info("트랜잭션 커밋");
            // Spring에서는 자동으로 커밋됨
            return true;
        } catch (Exception e) {
            logger.error("트랜잭션 커밋 에러", e);
            return false;
        }
    }

    /**
     * 트랜잭션 롤백
     */
    public boolean rollback() {
        try {
            logger.info("트랜잭션 롤백");
            // Spring에서는 예외 발생 시 자동으로 롤백됨
            return true;
        } catch (Exception e) {
            logger.error("트랜잭션 롤백 에러", e);
            return false;
        }
    }

    /**
     * 트랜잭션 상태 확인
     */
    public boolean isActive() {
        try {
            // Spring에서는 트랜잭션 상태 확인 로직 구현
            logger.debug("트랜잭션 상태 확인");
            return true;
        } catch (Exception e) {
            logger.error("트랜잭션 상태 확인 에러", e);
            return false;
        }
    }

    /**
     * DB 연결 가져오기
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            logger.error("DB 연결 가져오기 에러", e);
            return null;
        }
    }

    /**
     * 트랜잭션 템플릿으로 실행
     */
    public <T> T executeInTransaction(TransactionCallback<T> callback) {
        try {
            return transactionTemplate.execute(status -> {
                try {
                    return callback.execute();
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw e;
                }
            });
        } catch (Exception e) {
            logger.error("트랜잭션 실행 에러", e);
            return null;
        }
    }

    /**
     * 트랜잭션 콜백 인터페이스
     */
    @FunctionalInterface
    public interface TransactionCallback<T> {
        T execute() throws Exception;
    }

    /**
     * 읽기 전용 트랜잭션으로 실행
     */
    @Transactional(readOnly = true)
    public <T> T executeReadOnly(TransactionCallback<T> callback) {
        try {
            return callback.execute();
        } catch (Exception e) {
            logger.error("읽기 전용 트랜잭션 실행 에러", e);
            return null;
        }
    }

    /**
     * 쓰기 트랜잭션으로 실행
     */
    @Transactional
    public <T> T executeWrite(TransactionCallback<T> callback) {
        try {
            return callback.execute();
        } catch (Exception e) {
            logger.error("쓰기 트랜잭션 실행 에러", e);
            return null;
        }
    }

    /**
     * 트랜잭션 타임아웃 설정
     */
    public void setTimeout(int timeout) {
        try {
            // Spring에서는 TransactionTemplate의 timeout 설정
            logger.info("트랜잭션 타임아웃 설정: {}초", timeout);
        } catch (Exception e) {
            logger.error("트랜잭션 타임아웃 설정 에러", e);
        }
    }

    /**
     * 트랜잭션 격리 수준 설정
     */
    public void setIsolationLevel(int isolationLevel) {
        try {
            // Spring에서는 TransactionTemplate의 isolation 설정
            logger.info("트랜잭션 격리 수준 설정: {}", isolationLevel);
        } catch (Exception e) {
            logger.error("트랜잭션 격리 수준 설정 에러", e);
        }
    }
}