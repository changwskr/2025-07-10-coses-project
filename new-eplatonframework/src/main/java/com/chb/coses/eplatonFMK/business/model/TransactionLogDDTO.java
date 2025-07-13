package com.chb.coses.eplatonFMK.business.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 트랜잭션 로그 DTO 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public class TransactionLogDDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String registerDate;
    private String inTime;
    private String outTime;

    /**
     * 기본 생성자
     */
    public TransactionLogDDTO() {
        super();
    }

    /**
     * 모든 필드를 포함한 생성자
     */
    public TransactionLogDDTO(String transactionId, String methodName, String systemName,
            String hostName, String bankCode, String branchCode, String userId,
            String channelType, String businessDate, String eventNo, String ipAddress) {
        this.transactionId = transactionId;
        this.methodName = methodName;
        this.systemName = systemName;
        this.hostName = hostName;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.userId = userId;
        this.channelType = channelType;
        this.businessDate = businessDate;
        this.eventNo = eventNo;
        this.ipAddress = ipAddress;
    }

    // Getters and Setters
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    @Override
    public String toString() {
        return "TransactionLogDDTO{" +
                "transactionId='" + transactionId + '\'' +
                ", methodName='" + methodName + '\'' +
                ", systemName='" + systemName + '\'' +
                ", hostName='" + hostName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", userId='" + userId + '\'' +
                ", channelType='" + channelType + '\'' +
                ", businessDate='" + businessDate + '\'' +
                ", eventNo='" + eventNo + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", transactionNo='" + transactionNo + '\'' +
                ", responseTime='" + responseTime + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", inTime='" + inTime + '\'' +
                ", outTime='" + outTime + '\'' +
                '}';
    }
}