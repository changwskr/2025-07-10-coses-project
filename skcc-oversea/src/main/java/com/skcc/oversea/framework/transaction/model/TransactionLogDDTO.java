package com.skcc.oversea.framework.transaction.model;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.eplatonframework.transfer.*;
import com.skcc.oversea.foundation.utility.CommonUtil;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.framework.transaction.dao.*;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 * 2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 * 
 * @author : 장우승(WooSungJang)
 * @company: IMS SYSTEM
 * @email : changwskr@yahoo.co.kr
 * @version 1.0
 *          =============================================================================
 */
public class TransactionLogDDTO extends DTO {
    private String transactionId;
    private String hostName;
    private String systemName;
    private String methodName;
    private String bankCode;
    private String branchCode;
    private String userId;
    private String channelType;
    private String businessDate;
    private String registerDate;
    private String inTime;
    private String eventNo;
    private String transactionNo;
    private String outTime;
    private String responseTime;
    private String errorCode;
    private String IPAddress;
    private String org_seq;
    private String tpfq;

    public TransactionLogDDTO() {
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getBusinessDate() {
        return this.businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getRegisterDate() {
        return this.registerDate;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getInTime() {
        return this.inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getEventNo() {
        return this.eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getTransactionNo() {
        return this.transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getOutTime() {
        return this.outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getResponseTime() {
        return this.responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getOrg_seq() {
        return org_seq;
    }

    public void setOrg_seq(String org_seq) {
        this.org_seq = org_seq;
    }

    public String getTpfq() {
        return tpfq;
    }

    public void setTpfq(String tpfq) {
        this.tpfq = tpfq;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
        result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
        result = prime * result + ((systemName == null) ? 0 : systemName.hashCode());
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result + ((bankCode == null) ? 0 : bankCode.hashCode());
        result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((channelType == null) ? 0 : channelType.hashCode());
        result = prime * result + ((businessDate == null) ? 0 : businessDate.hashCode());
        result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
        result = prime * result + ((inTime == null) ? 0 : inTime.hashCode());
        result = prime * result + ((eventNo == null) ? 0 : eventNo.hashCode());
        result = prime * result + ((transactionNo == null) ? 0 : transactionNo.hashCode());
        result = prime * result + ((outTime == null) ? 0 : outTime.hashCode());
        result = prime * result + ((responseTime == null) ? 0 : responseTime.hashCode());
        result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
        result = prime * result + ((IPAddress == null) ? 0 : IPAddress.hashCode());
        result = prime * result + ((org_seq == null) ? 0 : org_seq.hashCode());
        result = prime * result + ((tpfq == null) ? 0 : tpfq.hashCode());
        return result;
    }
}
