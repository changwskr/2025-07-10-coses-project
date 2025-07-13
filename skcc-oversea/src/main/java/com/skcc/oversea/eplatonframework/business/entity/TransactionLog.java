package com.skcc.oversea.eplatonframework.business.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Transaction Log Entity for JPA
 */
@Entity
@Table(name = "transaction_log")
public class TransactionLog extends BaseEntity {

    @Column(name = "transaction_id", length = 50, nullable = false)
    private String transactionId;

    @Column(name = "transaction_no", length = 50)
    private String transactionNo;

    @Column(name = "host_name", length = 100)
    private String hostName;

    @Column(name = "system_name", length = 50)
    private String systemName;

    @Column(name = "method_name", length = 100)
    private String methodName;

    @Column(name = "bank_code", length = 10)
    private String bankCode;

    @Column(name = "branch_code", length = 10)
    private String branchCode;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "channel_type", length = 20)
    private String channelType;

    @Column(name = "business_date", length = 8)
    private String businessDate;

    @Column(name = "register_date", length = 8)
    private String registerDate;

    @Column(name = "in_time", length = 6)
    private String inTime;

    @Column(name = "out_time", length = 6)
    private String outTime;

    @Column(name = "response_time")
    private Long responseTime;

    @Column(name = "error_code", length = 10)
    private String errorCode;

    @Column(name = "event_no", length = 20)
    private String eventNo;

    @Column(name = "ip_address", length = 15)
    private String ipAddress;

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
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
}