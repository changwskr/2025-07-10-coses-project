package com.skcc.oversea.eplatonframework.transfer;

import com.skcc.oversea.framework.transfer.*;

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
public class EPlatonCommonDTO extends DTO implements IDTO {

    private String terminalID;
    private String terminalType;
    private String xmlSeq;
    private String bankCode;
    private String branchCode;
    private String glPostBranchCode;
    private String channelType;
    private String userID;
    private String eventNo;
    private String nation;
    private String regionCode;
    private String timeZone;
    private int fxRateCount;
    private String reqName;
    private String systemDate;
    private String businessDate;
    private String systemInTime;
    private String systemOutTime;
    private String transactionNo;
    private String baseCurrency;
    private String multiPL;
    private int userLevel;
    private String IPAddress;
    private String message;
    private String status;

    /**
     * 기본 생셩자
     */
    public EPlatonCommonDTO() {
        terminalID = "*";
        terminalType = "*";
        xmlSeq = "*";
        bankCode = "*";
        branchCode = "*";
        glPostBranchCode = "*";
        channelType = "*";
        userID = "*";
        eventNo = "*";
        nation = "*";
        regionCode = "*";
        timeZone = "*";
        fxRateCount = 0;
        reqName = "*";
        systemDate = "*";
        businessDate = "*";
        systemInTime = "*";
        systemOutTime = "*";
        transactionNo = "*";
        baseCurrency = "*";
        multiPL = "*";
        userLevel = 0;
        IPAddress = "*";
        message = "";
        status = "*";

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
     * Method getMultiPL.
     * 
     * @return String
     */
    public String getIPAddress() {
        return IPAddress;
    }

    /**
     * Method setMultiPL.
     * 
     * @param multiPL
     */
    public void setgetIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    /**
     * Method getMessage.
     * 
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method setMessage.
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EPlatonCommonDTO other = (EPlatonCommonDTO) obj;
        if (baseCurrency == null) {
            if (other.baseCurrency != null)
                return false;
        } else if (!baseCurrency.equals(other.baseCurrency))
            return false;
        if (bankCode == null) {
            if (other.bankCode != null)
                return false;
        } else if (!bankCode.equals(other.bankCode))
            return false;
        if (branchCode == null) {
            if (other.branchCode != null)
                return false;
        } else if (!branchCode.equals(other.branchCode))
            return false;
        if (businessDate == null) {
            if (other.businessDate != null)
                return false;
        } else if (!businessDate.equals(other.businessDate))
            return false;
        if (channelType == null) {
            if (other.channelType != null)
                return false;
        } else if (!channelType.equals(other.channelType))
            return false;
        if (eventNo == null) {
            if (other.eventNo != null)
                return false;
        } else if (!eventNo.equals(other.eventNo))
            return false;
        if (fxRateCount != other.fxRateCount)
            return false;
        if (glPostBranchCode == null) {
            if (other.glPostBranchCode != null)
                return false;
        } else if (!glPostBranchCode.equals(other.glPostBranchCode))
            return false;
        if (nation == null) {
            if (other.nation != null)
                return false;
        } else if (!nation.equals(other.nation))
            return false;
        if (regionCode == null) {
            if (other.regionCode != null)
                return false;
        } else if (!regionCode.equals(other.regionCode))
            return false;
        if (reqName == null) {
            if (other.reqName != null)
                return false;
        } else if (!reqName.equals(other.reqName))
            return false;
        if (systemDate == null) {
            if (other.systemDate != null)
                return false;
        } else if (!systemDate.equals(other.systemDate))
            return false;
        if (systemInTime == null) {
            if (other.systemInTime != null)
                return false;
        } else if (!systemInTime.equals(other.systemInTime))
            return false;
        if (systemOutTime == null) {
            if (other.systemOutTime != null)
                return false;
        } else if (!systemOutTime.equals(other.systemOutTime))
            return false;
        if (terminalID == null) {
            if (other.terminalID != null)
                return false;
        } else if (!terminalID.equals(other.terminalID))
            return false;
        if (terminalType == null) {
            if (other.terminalType != null)
                return false;
        } else if (!terminalType.equals(other.terminalType))
            return false;
        if (timeZone == null) {
            if (other.timeZone != null)
                return false;
        } else if (!timeZone.equals(other.timeZone))
            return false;
        if (transactionNo == null) {
            if (other.transactionNo != null)
                return false;
        } else if (!transactionNo.equals(other.transactionNo))
            return false;
        if (userLevel != other.userLevel)
            return false;
        if (userID == null) {
            if (other.userID != null)
                return false;
        } else if (!userID.equals(other.userID))
            return false;
        if (xmlSeq == null) {
            if (other.xmlSeq != null)
                return false;
        } else if (!xmlSeq.equals(other.xmlSeq))
            return false;
        if (multiPL == null) {
            if (other.multiPL != null)
                return false;
        } else if (!multiPL.equals(other.multiPL))
            return false;
        if (IPAddress == null) {
            if (other.IPAddress != null)
                return false;
        } else if (!IPAddress.equals(other.IPAddress))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baseCurrency == null) ? 0 : baseCurrency.hashCode());
        result = prime * result + ((bankCode == null) ? 0 : bankCode.hashCode());
        result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
        result = prime * result + ((businessDate == null) ? 0 : businessDate.hashCode());
        result = prime * result + ((channelType == null) ? 0 : channelType.hashCode());
        result = prime * result + ((eventNo == null) ? 0 : eventNo.hashCode());
        result = prime * result + fxRateCount;
        result = prime * result + ((glPostBranchCode == null) ? 0 : glPostBranchCode.hashCode());
        result = prime * result + ((nation == null) ? 0 : nation.hashCode());
        result = prime * result + ((regionCode == null) ? 0 : regionCode.hashCode());
        result = prime * result + ((reqName == null) ? 0 : reqName.hashCode());
        result = prime * result + ((systemDate == null) ? 0 : systemDate.hashCode());
        result = prime * result + ((systemInTime == null) ? 0 : systemInTime.hashCode());
        result = prime * result + ((systemOutTime == null) ? 0 : systemOutTime.hashCode());
        result = prime * result + ((terminalID == null) ? 0 : terminalID.hashCode());
        result = prime * result + ((terminalType == null) ? 0 : terminalType.hashCode());
        result = prime * result + ((timeZone == null) ? 0 : timeZone.hashCode());
        result = prime * result + ((transactionNo == null) ? 0 : transactionNo.hashCode());
        result = prime * result + userLevel;
        result = prime * result + ((userID == null) ? 0 : userID.hashCode());
        result = prime * result + ((xmlSeq == null) ? 0 : xmlSeq.hashCode());
        result = prime * result + ((multiPL == null) ? 0 : multiPL.hashCode());
        result = prime * result + ((IPAddress == null) ? 0 : IPAddress.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "EPlatonCommonDTO{" +
                "terminalID='" + terminalID + '\'' +
                ", terminalType='" + terminalType + '\'' +
                ", xmlSeq='" + xmlSeq + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", glPostBranchCode='" + glPostBranchCode + '\'' +
                ", channelType='" + channelType + '\'' +
                ", userID='" + userID + '\'' +
                ", eventNo='" + eventNo + '\'' +
                ", nation='" + nation + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", fxRateCount=" + fxRateCount +
                ", reqName='" + reqName + '\'' +
                ", systemDate='" + systemDate + '\'' +
                ", businessDate='" + businessDate + '\'' +
                ", systemInTime='" + systemInTime + '\'' +
                ", systemOutTime='" + systemOutTime + '\'' +
                ", transactionNo='" + transactionNo + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", multiPL='" + multiPL + '\'' +
                ", userLevel=" + userLevel +
                ", IPAddress='" + IPAddress + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
