package com.chb.coses.eplatonFMK.business.model;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.chb.coses.framework.transfer.DTO;

public class TransactionLogDDTO extends DTO{
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
    private long responseTime;
    private String errorCode;
    private String IPAddress;

    ////////////////////////////////////////////////////////////////////////////
    // 추가 모듈 향후 구성
    ////////////////////////////////////////////////////////////////////////////
    private String system_name;
    private String operation_name;
    private String cdto_name;
    private String action_name;
    private String hostseq;
    private String orgseq;
    private String tx_timer;
    private String tpfq;
    private String errorcode;
    private String trclass;
    private String web_timeout;
    private String web_intime;
    private String web_outtime;
    private String systemInTime;
    private String systemOutTime;
    private String system_date;
    private String error_message;
    ////////////////////////////////////////////////////////////////////////////
    // 추가 모듈 향후 구성해야 되며, 이것은 여러개의 정보가 존재 가능하다. 일단 하나로 둔다.
    ////////////////////////////////////////////////////////////////////////////
    private String call_service_name;
    private String call_tpm_in_time;
    private String call_tpm_out_time;
    private String call_tpme_interval;
    private String call_tpm_stf_in_time;
    private String call_tpm_stf_out_time;
    private String call_tpm_etf_in_time;
    private String call_tpm_etf_out_time;
    private String call_tpm_service_interval;
    private String error_code;
    private String call_hostseq;
    private String call_orgseq;
    private String call_location;


    public TransactionLogDDTO()
    {
    }
    public String getTransactionId()
    {
        return this.transactionId;
    }
    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }
    public String getHostName()
    {
        return this.hostName;
    }
    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }
    public String getSystemName()
    {
        return this.systemName;
    }
    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }
    public String getMethodName()
    {
        return this.methodName;
    }
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    public String getBankCode()
    {
        return this.bankCode;
    }
    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }
    public String getBranchCode()
    {
        return this.branchCode;
    }
    public String getUserId()
    {
        return this.userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }
    public String getChannelType()
    {
        return this.channelType;
    }
    public void setChannelType(String channelType)
    {
        this.channelType = channelType;
    }
    public String getBusinessDate()
    {
        return this.businessDate;
    }
    public void setBusinessDate(String businessDate)
    {
        this.businessDate = businessDate;
    }
    public String getRegisterDate()
    {
        return this.registerDate;
    }
    public String getIPAddress()
    {
        return IPAddress;
    }

    public void setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
    }
    public String getInTime()
    {
        return this.inTime;
    }
    public void setInTime(String inTime)
    {
        this.inTime = inTime;
    }
    public String getEventNo()
    {
        return this.eventNo;
    }
    public void setEventNo(String eventNo)
    {
        this.eventNo = eventNo;
    }
    public String getTransactionNo()
    {
        return this.transactionNo;
    }
    public void setTransactionNo(String transactionNo)
    {
        this.transactionNo = transactionNo;
    }
    public String getOutTime()
    {
        return this.outTime;
    }
    public void setOutTime(String outTime)
    {
        this.outTime = outTime;
    }
    public long getResponseTime()
    {
        return this.responseTime;
    }
    public void setResponseTime(long responseTime)
    {
        this.responseTime = responseTime;
    }
    public String getErrorCode()
    {
        return this.errorCode;
    }
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    public void setIPAddress(String IPAddress)
    {
        this.IPAddress = IPAddress;
    }
  public String getCall_hostseq()
  {
    return call_hostseq;
  }
  public void setCall_hostseq(String call_hostseq)
  {
    this.call_hostseq = call_hostseq;
  }
  public String getCall_location()
  {
    return call_location;
  }
  public void setCall_location(String call_location)
  {
    this.call_location = call_location;
  }
  public String getCall_orgseq()
  {
    return call_orgseq;
  }
  public void setCall_orgseq(String call_orgseq)
  {
    this.call_orgseq = call_orgseq;
  }
  public String getCall_service_name()
  {
    return call_service_name;
  }
  public void setCall_service_name(String call_service_name)
  {
    this.call_service_name = call_service_name;
  }
  public String getCall_tpm_etf_in_time()
  {
    return call_tpm_etf_in_time;
  }
  public void setCall_tpm_etf_in_time(String call_tpm_etf_in_time)
  {
    this.call_tpm_etf_in_time = call_tpm_etf_in_time;
  }
  public String getCall_tpm_etf_out_time()
  {
    return call_tpm_etf_out_time;
  }
  public void setCall_tpm_etf_out_time(String call_tpm_etf_out_time)
  {
    this.call_tpm_etf_out_time = call_tpm_etf_out_time;
  }
  public String getCall_tpm_in_time()
  {
    return call_tpm_in_time;
  }
  public void setCall_tpm_in_time(String call_tpm_in_time)
  {
    this.call_tpm_in_time = call_tpm_in_time;
  }
  public String getCall_tpm_out_time()
  {
    return call_tpm_out_time;
  }
  public void setCall_tpm_out_time(String call_tpm_out_time)
  {
    this.call_tpm_out_time = call_tpm_out_time;
  }
  public String getCall_tpm_service_interval()
  {
    return call_tpm_service_interval;
  }
  public void setCall_tpm_service_interval(String call_tpm_service_interval)
  {
    this.call_tpm_service_interval = call_tpm_service_interval;
  }
  public String getCall_tpm_stf_in_time()
  {
    return call_tpm_stf_in_time;
  }
  public void setCall_tpm_stf_in_time(String call_tpm_stf_in_time)
  {
    this.call_tpm_stf_in_time = call_tpm_stf_in_time;
  }
  public String getCall_tpm_stf_out_time()
  {
    return call_tpm_stf_out_time;
  }
  public void setCall_tpm_stf_out_time(String call_tpm_stf_out_time)
  {
    this.call_tpm_stf_out_time = call_tpm_stf_out_time;
  }
  public String getCall_tpme_interval()
  {
    return call_tpme_interval;
  }
  public void setCall_tpme_interval(String call_tpme_interval)
  {
    this.call_tpme_interval = call_tpme_interval;
  }
  public String getCdto_name()
  {
    return cdto_name;
  }
  public void setCdto_name(String cdto_name)
  {
    this.cdto_name = cdto_name;
  }
  public String getError_code()
  {
    return error_code;
  }
  public void setError_code(String error_code)
  {
    this.error_code = error_code;
  }
  public String getError_message()
  {
    return error_message;
  }
  public void setError_message(String error_message)
  {
    this.error_message = error_message;
  }
  public String getErrorcode()
  {
    return errorcode;
  }
  public void setErrorcode(String errorcode)
  {
    this.errorcode = errorcode;
  }
  public String getHostseq()
  {
    return hostseq;
  }
  public void setHostseq(String hostseq)
  {
    this.hostseq = hostseq;
  }
  public String getOperation_name()
  {
    return operation_name;
  }
  public void setOperation_name(String operation_name)
  {
    this.operation_name = operation_name;
  }
  public String getOrgseq()
  {
    return orgseq;
  }
  public void setOrgseq(String orgseq)
  {
    this.orgseq = orgseq;
  }
  public String getSystem_date()
  {
    return system_date;
  }
  public void setSystem_date(String system_date)
  {
    this.system_date = system_date;
  }
  public String getSystem_name()
  {
    return system_name;
  }
  public void setSystem_name(String system_name)
  {
    this.system_name = system_name;
  }
  public String getSystemInTime()
  {
    return systemInTime;
  }
  public void setSystemInTime(String systemInTime)
  {
    this.systemInTime = systemInTime;
  }
  public String getSystemOutTime()
  {
    return systemOutTime;
  }
  public void setSystemOutTime(String systemOutTime)
  {
    this.systemOutTime = systemOutTime;
  }
  public String getTpfq()
  {
    return tpfq;
  }
  public void setTpfq(String tpfq)
  {
    this.tpfq = tpfq;
  }
  public String getTrclass()
  {
    return trclass;
  }
  public void setTrclass(String trclass)
  {
    this.trclass = trclass;
  }
  public String getTx_timer()
  {
    return tx_timer;
  }
  public void setTx_timer(String tx_timer)
  {
    this.tx_timer = tx_timer;
  }
  public String getWeb_intime()
  {
    return web_intime;
  }
  public void setWeb_intime(String web_intime)
  {
    this.web_intime = web_intime;
  }
  public String getWeb_outtime()
  {
    return web_outtime;
  }
  public void setWeb_outtime(String web_outtime)
  {
    this.web_outtime = web_outtime;
  }
  public String getWeb_timeout()
  {
    return web_timeout;
  }
  public void setWeb_timeout(String web_timeout)
  {
    this.web_timeout = web_timeout;
  }
  public String getAction_name()
  {
    return action_name;
  }
  public void setAction_name(String action_name)
  {
    this.action_name = action_name;
  }
}


