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
 *  2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 *                                                        @author : 장우승(WooSungJang)
 *                                                        @company: IMS SYSTEM
 *                                                        @email  : changwskr@yahoo.co.kr
 *                                                        @version 1.0
 *  =============================================================================
 */
public class TransactionUpDownDDTO extends DTO
{
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


  public TransactionUpDownDDTO()
  {
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


}




