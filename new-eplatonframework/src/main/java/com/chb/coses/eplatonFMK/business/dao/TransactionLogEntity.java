package com.chb.coses.eplatonFMK.business.dao;

import java.sql.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 트랜잭션 로그 엔티티 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public class TransactionLogEntity extends AbstractEntity {

    private static final Logger logger = LoggerFactory.getLogger(TransactionLogEntity.class);

    private String transactionId;
    private String methodName;
    private String systemName;
    private String hostName;
    private String bankCode;
    private String branchCode;
    private String userId;
    private String channelType;
    private String businessDate;
    private String eventNo;
    private String ipAddress;
    private String transactionNo;
    private String responseTime;
    private String errorCode;
    private Date registerDate;
    private Date inTime;
    private Date outTime;

    /**
     * 기본 생성자
     */
    public TransactionLogEntity() {
        super();
    }

    /**
     * 트랜잭션 입력 로그 삽입
     * 
     * @param con           데이터베이스 연결
     * @param transactionId 트랜잭션 ID
     * @param methodName    메소드명
     * @param systemName    시스템명
     * @param hostName      호스트명
     * @param bankCode      은행 코드
     * @param branchCode    지점 코드
     * @param userId        사용자 ID
     * @param channelType   채널 타입
     * @param businessDate  비즈니스 날짜
     * @param eventNo       이벤트 번호
     * @param ipAddress     IP 주소
     * @throws SQLException SQL 예외
     */
    public static void insertTransactionInLog(Connection con, String transactionId,
            String methodName, String systemName, String hostName,
            String bankCode, String branchCode, String userId,
            String channelType, String businessDate,
            String eventNo, String ipAddress) throws SQLException {

        String sql = "INSERT INTO TRANSACTION_IN_LOG (TRANSACTION_ID, METHOD_NAME, SYSTEM_NAME, " +
                "HOST_NAME, BANK_CODE, BRANCH_CODE, USER_ID, CHANNEL_TYPE, BUSINESS_DATE, " +
                "EVENT_NO, IP_ADDRESS, REGISTER_DATE, IN_TIME) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, SYSDATE)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, transactionId);
            pstmt.setString(2, methodName);
            pstmt.setString(3, systemName);
            pstmt.setString(4, hostName);
            pstmt.setString(5, bankCode);
            pstmt.setString(6, branchCode);
            pstmt.setString(7, userId);
            pstmt.setString(8, channelType);
            pstmt.setString(9, businessDate);
            pstmt.setString(10, eventNo);
            pstmt.setString(11, ipAddress);

            pstmt.executeUpdate();
            logger.debug("Transaction in log inserted: {}", transactionId);
        }
    }

    /**
     * 트랜잭션 출력 로그 삽입
     * 
     * @param con           데이터베이스 연결
     * @param transactionId 트랜잭션 ID
     * @param transactionNo 트랜잭션 번호
     * @param responseTime  응답 시간
     * @param errorCode     에러 코드
     * @throws SQLException SQL 예외
     */
    public static void insertTransactionOutLog(Connection con, String transactionId,
            String transactionNo, String responseTime,
            String errorCode) throws SQLException {

        String sql = "UPDATE TRANSACTION_IN_LOG SET TRANSACTION_NO = ?, RESPONSE_TIME = ?, " +
                "ERROR_CODE = ?, OUT_TIME = SYSDATE WHERE TRANSACTION_ID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, transactionNo);
            pstmt.setString(2, responseTime);
            pstmt.setString(3, errorCode);
            pstmt.setString(4, transactionId);

            int updatedRows = pstmt.executeUpdate();
            logger.debug("Transaction out log updated: {} rows affected", updatedRows);
        }
    }

    /**
     * 트랜잭션 로그를 파일에 기록
     * 
     * @param logLine 로그 라인
     */
    public static void insertTransactionLog2File(String logLine) {
        logger.info("Transaction log: {}", logLine);
    }

    // Getters and Setters
    @Override
    public Object getId() {
        return transactionId;
    }

    @Override
    public void setId(Object id) {
        this.transactionId = (String) id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }
}