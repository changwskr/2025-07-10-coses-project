package com.skcc.oversea.framework.transaction.model;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.foundation.utility.CommonUtil;

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
public class TransactionUpDownDDTO extends DTO {
  ////////////////////////////////////////////////////////////////////////////
  // COMMONDTO
  ////////////////////////////////////////////////////////////////////////////
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

  private String system_name;
  private String operation_name;
  private String operation_method_name;
  private String cdto_name;
  private String action_name;
  private String hostseq;
  private String orgseq;
  private String tx_timer;
  private String tpfq;
  private String errorcode;
  private String trclass;
  private String bp_hostseq;
  private String web_timeout;
  private String web_intime;
  private String web_outtime;
  private String system_date;
  private String error_message;
  private String host_name = CommonUtil.GetHostName();
  ////////////////////////////////////////////////////////////////////////////
  // REQUESTCDTO
  ////////////////////////////////////////////////////////////////////////////
  private String request_cdto;
  ////////////////////////////////////////////////////////////////////////////
  // RESPONSECDTO
  ////////////////////////////////////////////////////////////////////////////
  private String response_cdto;

  public TransactionUpDownDDTO() {
  }

  public String getBankCode() {
    return bankCode;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getBranchCode() {
    return branchCode;
  }

  public String getBusinessDate() {
    return businessDate;
  }

  public String getChannelType() {
    return channelType;
  }

  public String getEventNo() {
    return eventNo;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  public void setBranchCode(String branchCode) {
    this.branchCode = branchCode;
  }

  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }

  public void setChannelType(String channelType) {
    this.channelType = channelType;
  }

  public void setEventNo(String eventNo) {
    this.eventNo = eventNo;
  }

  public void setFxRateCount(int fxRateCount) {
    this.fxRateCount = fxRateCount;
  }

  public void setGlPostBranchCode(String glPostBranchCode) {
    this.glPostBranchCode = glPostBranchCode;
  }

  public void setIPAddress(String IPAddress) {
    this.IPAddress = IPAddress;
  }

  public void setMultiPL(String multiPL) {
    this.multiPL = multiPL;
  }

  public int getFxRateCount() {
    return fxRateCount;
  }

  public String getGlPostBranchCode() {
    return glPostBranchCode;
  }

  public String getIPAddress() {
    return IPAddress;
  }

  public String getMultiPL() {
    return multiPL;
  }

  public String getNation() {
    return nation;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public String getReqName() {
    return reqName;
  }

  public String getSystemDate() {
    return systemDate;
  }

  public String getSystemInTime() {
    return systemInTime;
  }

  public String getSystemOutTime() {
    return systemOutTime;
  }

  public String getTerminalID() {
    return terminalID;
  }

  public String getTerminalType() {
    return terminalType;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public String getTransactionNo() {
    return transactionNo;
  }

  public String getUserID() {
    return userID;
  }

  public int getUserLevel() {
    return userLevel;
  }

  public String getXmlSeq() {
    return xmlSeq;
  }

  public void setSystemInTime(String systemInTime) {
    this.systemInTime = systemInTime;
  }

  public void setSystemDate(String systemDate) {
    this.systemDate = systemDate;
  }

  public void setReqName(String reqName) {
    this.reqName = reqName;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public void setTerminalType(String terminalType) {
    this.terminalType = terminalType;
  }

  public void setTransactionNo(String transactionNo) {
    this.transactionNo = transactionNo;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public void setUserLevel(int userLevel) {
    this.userLevel = userLevel;
  }

  public void setXmlSeq(String xmlSeq) {
    this.xmlSeq = xmlSeq;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public void setTerminalID(String terminalID) {
    this.terminalID = terminalID;
  }

  public void setSystemOutTime(String systemOutTime) {
    this.systemOutTime = systemOutTime;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }

  public String getAction_name() {
    return action_name;
  }

  public void setAction_name(String action_name) {
    this.action_name = action_name;
  }

  public String getCdto_name() {
    return cdto_name;
  }

  public void setCdto_name(String cdto_name) {
    this.cdto_name = cdto_name;
  }

  public String getError_message() {
    return error_message;
  }

  public void setError_message(String error_message) {
    this.error_message = error_message;
  }

  public void setErrorcode(String errorcode) {
    this.errorcode = errorcode;
  }

  public String getErrorcode() {
    return errorcode;
  }

  public String getHostseq() {
    return hostseq;
  }

  public void setHostseq(String hostseq) {
    this.hostseq = hostseq;
  }

  public String getOperation_method_name() {
    return operation_method_name;
  }

  public void setOperation_method_name(String operation_method_name) {
    this.operation_method_name = operation_method_name;
  }

  public void setOperation_name(String operation_name) {
    this.operation_name = operation_name;
  }

  public String getOperation_name() {
    return operation_name;
  }

  public String getOrgseq() {
    return orgseq;
  }

  public void setOrgseq(String orgseq) {
    this.orgseq = orgseq;
  }

  public void setRequest_cdto(String request_cdto) {
    this.request_cdto = request_cdto;
  }

  public String getRequest_cdto() {
    return request_cdto;
  }

  public String getResponse_cdto() {
    return response_cdto;
  }

  public String getSystem_date() {
    return system_date;
  }

  public String getSystem_name() {
    return system_name;
  }

  public void setSystem_name(String system_name) {
    this.system_name = system_name;
  }

  public void setSystem_date(String system_date) {
    this.system_date = system_date;
  }

  public void setResponse_cdto(String response_cdto) {
    this.response_cdto = response_cdto;
  }

  public String getTpfq() {
    return tpfq;
  }

  public void setTpfq(String tpfq) {
    this.tpfq = tpfq;
  }

  public void setTrclass(String trclass) {
    this.trclass = trclass;
  }

  public String getTrclass() {
    return trclass;
  }

  public String getTx_timer() {
    return tx_timer;
  }

  public void setTx_timer(String tx_timer) {
    this.tx_timer = tx_timer;
  }

  public String getWeb_intime() {
    return web_intime;
  }

  public String getWeb_outtime() {
    return web_outtime;
  }

  public String getWeb_timeout() {
    return web_timeout;
  }

  public void setWeb_intime(String web_intime) {
    this.web_intime = web_intime;
  }

  public void setWeb_outtime(String web_outtime) {
    this.web_outtime = web_outtime;
  }

  public void setWeb_timeout(String web_timeout) {
    this.web_timeout = web_timeout;
  }

  public String getHost_name() {
    return host_name;
  }

  public void setHost_name(String host_name) {
    this.host_name = host_name;
  }

  public String getBp_hostseq() {
    return bp_hostseq;
  }

  public void setBp_hostseq(String bp_hostseq) {
    this.bp_hostseq = bp_hostseq;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((terminalID == null) ? 0 : terminalID.hashCode());
    result = prime * result + ((terminalType == null) ? 0 : terminalType.hashCode());
    result = prime * result + ((xmlSeq == null) ? 0 : xmlSeq.hashCode());
    result = prime * result + ((bankCode == null) ? 0 : bankCode.hashCode());
    result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
    result = prime * result + ((glPostBranchCode == null) ? 0 : glPostBranchCode.hashCode());
    result = prime * result + ((channelType == null) ? 0 : channelType.hashCode());
    result = prime * result + ((userID == null) ? 0 : userID.hashCode());
    result = prime * result + ((eventNo == null) ? 0 : eventNo.hashCode());
    result = prime * result + ((nation == null) ? 0 : nation.hashCode());
    result = prime * result + ((regionCode == null) ? 0 : regionCode.hashCode());
    result = prime * result + ((timeZone == null) ? 0 : timeZone.hashCode());
    result = prime * result + fxRateCount;
    result = prime * result + ((reqName == null) ? 0 : reqName.hashCode());
    result = prime * result + ((systemDate == null) ? 0 : systemDate.hashCode());
    result = prime * result + ((businessDate == null) ? 0 : businessDate.hashCode());
    result = prime * result + ((systemInTime == null) ? 0 : systemInTime.hashCode());
    result = prime * result + ((systemOutTime == null) ? 0 : systemOutTime.hashCode());
    result = prime * result + ((transactionNo == null) ? 0 : transactionNo.hashCode());
    result = prime * result + ((baseCurrency == null) ? 0 : baseCurrency.hashCode());
    result = prime * result + ((multiPL == null) ? 0 : multiPL.hashCode());
    result = prime * result + userLevel;
    result = prime * result + ((IPAddress == null) ? 0 : IPAddress.hashCode());
    result = prime * result + ((system_name == null) ? 0 : system_name.hashCode());
    result = prime * result + ((operation_name == null) ? 0 : operation_name.hashCode());
    result = prime * result + ((operation_method_name == null) ? 0 : operation_method_name.hashCode());
    result = prime * result + ((cdto_name == null) ? 0 : cdto_name.hashCode());
    result = prime * result + ((action_name == null) ? 0 : action_name.hashCode());
    result = prime * result + ((hostseq == null) ? 0 : hostseq.hashCode());
    result = prime * result + ((orgseq == null) ? 0 : orgseq.hashCode());
    result = prime * result + ((tx_timer == null) ? 0 : tx_timer.hashCode());
    result = prime * result + ((tpfq == null) ? 0 : tpfq.hashCode());
    result = prime * result + ((errorcode == null) ? 0 : errorcode.hashCode());
    result = prime * result + ((trclass == null) ? 0 : trclass.hashCode());
    result = prime * result + ((bp_hostseq == null) ? 0 : bp_hostseq.hashCode());
    result = prime * result + ((web_timeout == null) ? 0 : web_timeout.hashCode());
    result = prime * result + ((web_intime == null) ? 0 : web_intime.hashCode());
    result = prime * result + ((web_outtime == null) ? 0 : web_outtime.hashCode());
    result = prime * result + ((system_date == null) ? 0 : system_date.hashCode());
    result = prime * result + ((error_message == null) ? 0 : error_message.hashCode());
    result = prime * result + ((host_name == null) ? 0 : host_name.hashCode());
    result = prime * result + ((request_cdto == null) ? 0 : request_cdto.hashCode());
    result = prime * result + ((response_cdto == null) ? 0 : response_cdto.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TransactionUpDownDDTO other = (TransactionUpDownDDTO) obj;
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
    if (xmlSeq == null) {
      if (other.xmlSeq != null)
        return false;
    } else if (!xmlSeq.equals(other.xmlSeq))
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
    if (channelType == null) {
      if (other.channelType != null)
        return false;
    } else if (!channelType.equals(other.channelType))
      return false;
    if (userID == null) {
      if (other.userID != null)
        return false;
    } else if (!userID.equals(other.userID))
      return false;
    if (eventNo == null) {
      if (other.eventNo != null)
        return false;
    } else if (!eventNo.equals(other.eventNo))
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
    if (timeZone == null) {
      if (other.timeZone != null)
        return false;
    } else if (!timeZone.equals(other.timeZone))
      return false;
    if (fxRateCount != other.fxRateCount)
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
    if (businessDate == null) {
      if (other.businessDate != null)
        return false;
    } else if (!businessDate.equals(other.businessDate))
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
    if (transactionNo == null) {
      if (other.transactionNo != null)
        return false;
    } else if (!transactionNo.equals(other.transactionNo))
      return false;
    if (baseCurrency == null) {
      if (other.baseCurrency != null)
        return false;
    } else if (!baseCurrency.equals(other.baseCurrency))
      return false;
    if (multiPL == null) {
      if (other.multiPL != null)
        return false;
    } else if (!multiPL.equals(other.multiPL))
      return false;
    if (userLevel != other.userLevel)
      return false;
    if (IPAddress == null) {
      if (other.IPAddress != null)
        return false;
    } else if (!IPAddress.equals(other.IPAddress))
      return false;
    if (system_name == null) {
      if (other.system_name != null)
        return false;
    } else if (!system_name.equals(other.system_name))
      return false;
    if (operation_name == null) {
      if (other.operation_name != null)
        return false;
    } else if (!operation_name.equals(other.operation_name))
      return false;
    if (operation_method_name == null) {
      if (other.operation_method_name != null)
        return false;
    } else if (!operation_method_name.equals(other.operation_method_name))
      return false;
    if (cdto_name == null) {
      if (other.cdto_name != null)
        return false;
    } else if (!cdto_name.equals(other.cdto_name))
      return false;
    if (action_name == null) {
      if (other.action_name != null)
        return false;
    } else if (!action_name.equals(other.action_name))
      return false;
    if (hostseq == null) {
      if (other.hostseq != null)
        return false;
    } else if (!hostseq.equals(other.hostseq))
      return false;
    if (orgseq == null) {
      if (other.orgseq != null)
        return false;
    } else if (!orgseq.equals(other.orgseq))
      return false;
    if (tx_timer == null) {
      if (other.tx_timer != null)
        return false;
    } else if (!tx_timer.equals(other.tx_timer))
      return false;
    if (tpfq == null) {
      if (other.tpfq != null)
        return false;
    } else if (!tpfq.equals(other.tpfq))
      return false;
    if (errorcode == null) {
      if (other.errorcode != null)
        return false;
    } else if (!errorcode.equals(other.errorcode))
      return false;
    if (trclass == null) {
      if (other.trclass != null)
        return false;
    } else if (!trclass.equals(other.trclass))
      return false;
    if (bp_hostseq == null) {
      if (other.bp_hostseq != null)
        return false;
    } else if (!bp_hostseq.equals(other.bp_hostseq))
      return false;
    if (web_timeout == null) {
      if (other.web_timeout != null)
        return false;
    } else if (!web_timeout.equals(other.web_timeout))
      return false;
    if (web_intime == null) {
      if (other.web_intime != null)
        return false;
    } else if (!web_intime.equals(other.web_intime))
      return false;
    if (web_outtime == null) {
      if (other.web_outtime != null)
        return false;
    } else if (!web_outtime.equals(other.web_outtime))
      return false;
    if (system_date == null) {
      if (other.system_date != null)
        return false;
    } else if (!system_date.equals(other.system_date))
      return false;
    if (error_message == null) {
      if (other.error_message != null)
        return false;
    } else if (!error_message.equals(other.error_message))
      return false;
    if (host_name == null) {
      if (other.host_name != null)
        return false;
    } else if (!host_name.equals(other.host_name))
      return false;
    if (request_cdto == null) {
      if (other.request_cdto != null)
        return false;
    } else if (!request_cdto.equals(other.request_cdto))
      return false;
    if (response_cdto == null) {
      if (other.response_cdto != null)
        return false;
    } else if (!response_cdto.equals(other.response_cdto))
      return false;
    return true;
  }
}
