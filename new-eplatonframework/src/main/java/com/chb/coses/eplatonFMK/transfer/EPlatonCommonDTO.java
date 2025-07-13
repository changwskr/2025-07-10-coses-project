package com.chb.coses.eplatonFMK.transfer;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.chb.coses.framework.transfer.DTO;

/**
 * EPlaton Common DTO
 * Spring 기반으로 전환된 공통 DTO 클래스
 * 
 * @author 이 해일 (hiyi@componentvision.com)
 * @version 1.30
 *
 *          이 class는 모든 request에 공통으로 포함되는 정보를 표현한다.
 */
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class EPlatonCommonDTO extends DTO {

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
    private String systemInTime;

    @JsonProperty("systemOutTime")
    private String systemOutTime;

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

    /**
     * 기본 생성자
     */
    public EPlatonCommonDTO() {
    }

    /**
     * Method getBaseCurrency.
     * 
     * @return String
     */
    public String getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * Method setBaseCurrency.
     * 
     * @param baseCurrency
     */
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    /**
     * Method getBankCode.
     * 
     * @return String
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Method getBranchCode.
     * 
     * @return String
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * Method getBusinessDate.
     * 
     * @return String
     */
    public String getBusinessDate() {
        return businessDate;
    }

    /**
     * Method getChannelType.
     * 
     * @return String
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * Method getEventNo.
     * 
     * @return String
     */
    public String getEventNo() {
        return eventNo;
    }

    /**
     * Method getFxRateCount.
     * 
     * @return int
     */
    public int getFxRateCount() {
        return fxRateCount;
    }

    /**
     * Method getGlPostBranchCode.
     * 
     * @return String
     */
    public String getGlPostBranchCode() {
        return glPostBranchCode;
    }

    /**
     * Method getNation.
     * 
     * @return String
     */
    public String getNation() {
        return nation;
    }

    /**
     * Method getRegionCode.
     * 
     * @return String
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * Method getReqName.
     * 
     * @return String
     */
    public String getReqName() {
        return reqName;
    }

    /**
     * Method getTerminalID.
     * 
     * @return String
     */
    public String getTerminalID() {
        return terminalID;
    }

    /**
     * Method getTerminalType.
     * 
     * @return String
     */
    public String getTerminalType() {
        return terminalType;
    }

    /**
     * Method getTimeZone.
     * 
     * @return String
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Method getSystemDate.
     * 
     * @return String
     */
    public String getSystemDate() {
        return systemDate;
    }

    /**
     * Method getSystemInTime.
     * 
     * @return String
     */
    public String getSystemInTime() {
        return systemInTime;
    }

    /**
     * Method getTransactionNo.
     * 
     * @return String
     */
    public String getTransactionNo() {
        return transactionNo;
    }

    /**
     * Method getSystemOutTime.
     * 
     * @return String
     */
    public String getSystemOutTime() {
        return systemOutTime;
    }

    /**
     * Method getUserID.
     * 
     * @return String
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Method getXmlSeq.
     * 
     * @return String
     */
    public String getXmlSeq() {
        return xmlSeq;
    }

    /**
     * Method getUserLevel.
     * 
     * @return int
     */
    public int getUserLevel() {
        return userLevel;
    }

    /**
     * Method setBankCode.
     * 
     * @param bankCode
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * Method setBranchCode.
     * 
     * @param branchCode
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * Method setBusinessDate.
     * 
     * @param businessDate
     */
    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    /**
     * Method setChannelType.
     * 
     * @param channelType
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    /**
     * Method setEventNo.
     * 
     * @param eventNo
     */
    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    /**
     * Method setFxRateCount.
     * 
     * @param fxRateCount
     */
    public void setFxRateCount(int fxRateCount) {
        this.fxRateCount = fxRateCount;
    }

    /**
     * Method setGlPostBranchCode.
     * 
     * @param glPostBranchCode
     */
    public void setGlPostBranchCode(String glPostBranchCode) {
        this.glPostBranchCode = glPostBranchCode;
    }

    /**
     * Method setNation.
     * 
     * @param nation
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * Method setRegionCode.
     * 
     * @param regionCode
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * Method setReqName.
     * 
     * @param reqName
     */
    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    /**
     * Method setTerminalID.
     * 
     * @param terminalID
     */
    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    /**
     * Method setTerminalType.
     * 
     * @param terminalType
     */
    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    /**
     * Method setTimeZone.
     * 
     * @param timeZone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Method setSystemDate.
     * 
     * @param systemDate
     */
    public void setSystemDate(String systemDate) {
        this.systemDate = systemDate;
    }

    /**
     * Method setSystemInTime.
     * 
     * @param systemInTime
     */
    public void setSystemInTime(String systemInTime) {
        this.systemInTime = systemInTime;
    }

    /**
     * Method setTransactionNo.
     * 
     * @param transactionNo
     */
    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    /**
     * Method setSystemOutTime.
     * 
     * @param systemOutTime
     */
    public void setSystemOutTime(String systemOutTime) {
        this.systemOutTime = systemOutTime;
    }

    /**
     * Method setUserID.
     * 
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Method setXmlSeq.
     * 
     * @param xmlSeq
     */
    public void setXmlSeq(String xmlSeq) {
        this.xmlSeq = xmlSeq;
    }

    /**
     * Method setUserLevel.
     * 
     * @param userLevel
     */
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * Method getMultiPL.
     * 
     * @return String
     */
    public String getMultiPL() {
        return multiPL;
    }

    /**
     * Method setMultiPL.
     * 
     * @param multiPL
     */
    public void setMultiPL(String multiPL) {
        this.multiPL = multiPL;
    }

    /**
     * Method getIPAddress.
     * 
     * @return String
     */
    public String getIPAddress() {
        return IPAddress;
    }

    /**
     * Method setIPAddress.
     * 
     * @param IPAddress
     */
    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}