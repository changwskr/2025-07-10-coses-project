package com.banking.neweplatonframework.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * New EPlaton Common DTO
 * 
 * Contains common data shared across all New EPlaton operations.
 * This replaces the legacy EPlatonCommonDTO with Spring Boot features.
 */
public class NewEPlatonCommonDTO {

    @JsonProperty("terminalID")
    private String terminalID;

    @JsonProperty("terminalType")
    private String terminalType;

    @JsonProperty("xmlSeq")
    private String xmlSeq;

    @JsonProperty("bankCode")
    private String bankCode;

    @JsonProperty("branchCode")
    private String branchCode;

    @JsonProperty("glPostBranchCode")
    private String glPostBranchCode;

    @JsonProperty("channelType")
    private String channelType;

    @JsonProperty("userID")
    private String userID;

    @JsonProperty("eventNo")
    private String eventNo;

    @JsonProperty("nation")
    private String nation;

    @JsonProperty("regionCode")
    private String regionCode;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("fxRateCount")
    private int fxRateCount;

    @JsonProperty("reqName")
    private String reqName;

    @JsonProperty("systemDate")
    private String systemDate;

    @JsonProperty("businessDate")
    private String businessDate;

    @JsonProperty("systemInTime")
    private LocalDateTime systemInTime;

    @JsonProperty("systemOutTime")
    private LocalDateTime systemOutTime;

    @JsonProperty("transactionNo")
    private String transactionNo;

    @JsonProperty("baseCurrency")
    private String baseCurrency;

    @JsonProperty("multiPL")
    private String multiPL;

    @JsonProperty("userLevel")
    private int userLevel;

    @JsonProperty("IPAddress")
    private String IPAddress;

    @JsonProperty("status")
    private String status;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    public NewEPlatonCommonDTO() {
        this.systemInTime = LocalDateTime.now();
        this.timeZone = "Asia/Seoul";
        this.nation = "KR";
        this.status = "INIT";
    }

    // Getters and Setters
    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getXmlSeq() {
        return xmlSeq;
    }

    public void setXmlSeq(String xmlSeq) {
        this.xmlSeq = xmlSeq;
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

    public String getGlPostBranchCode() {
        return glPostBranchCode;
    }

    public void setGlPostBranchCode(String glPostBranchCode) {
        this.glPostBranchCode = glPostBranchCode;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getFxRateCount() {
        return fxRateCount;
    }

    public void setFxRateCount(int fxRateCount) {
        this.fxRateCount = fxRateCount;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(String systemDate) {
        this.systemDate = systemDate;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public LocalDateTime getSystemInTime() {
        return systemInTime;
    }

    public void setSystemInTime(LocalDateTime systemInTime) {
        this.systemInTime = systemInTime;
    }

    public LocalDateTime getSystemOutTime() {
        return systemOutTime;
    }

    public void setSystemOutTime(LocalDateTime systemOutTime) {
        this.systemOutTime = systemOutTime;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getMultiPL() {
        return multiPL;
    }

    public void setMultiPL(String multiPL) {
        this.multiPL = multiPL;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Set error information
     */
    public void setError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = "ERROR";
        this.systemOutTime = LocalDateTime.now();
    }

    /**
     * Set success information
     */
    public void setSuccess() {
        this.errorCode = "IZZ000";
        this.errorMessage = "Success";
        this.status = "SUCCESS";
        this.systemOutTime = LocalDateTime.now();
    }

    /**
     * Check if operation was successful
     */
    public boolean isSuccess() {
        return "SUCCESS".equals(status) ||
                (errorCode != null && errorCode.startsWith("I"));
    }

    @Override
    public String toString() {
        return "NewEPlatonCommonDTO{" +
                "terminalID='" + terminalID + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", userID='" + userID + '\'' +
                ", transactionNo='" + transactionNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}