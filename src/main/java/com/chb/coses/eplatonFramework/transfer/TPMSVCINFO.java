package com.chb.coses.eplatonFramework.transfer;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


public class TPMSVCINFO {

  private char offset='X';
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


  public String getCall_hostseq()
  {
    return call_hostseq;
  }
  public String getCall_location()
  {
    return call_location;
  }
  public String getCall_orgseq()
  {
    return call_orgseq;
  }
  public String getCall_service_name()
  {
    return call_service_name;
  }
  public String getCall_tpm_etf_in_time()
  {
    return call_tpm_etf_in_time;
  }
  public String getCall_tpm_etf_out_time()
  {
    return call_tpm_etf_out_time;
  }
  public String getCall_tpm_in_time()
  {
    return call_tpm_in_time;
  }
  public String getCall_tpm_out_time()
  {
    return call_tpm_out_time;
  }
  public String getCall_tpm_stf_in_time()
  {
    return call_tpm_stf_in_time;
  }
  public String getCall_tpm_stf_out_time()
  {
    return call_tpm_stf_out_time;
  }
  public String getCall_tpme_interval()
  {
    return call_tpme_interval;
  }
  public String getCall_tpme_service_interval()
  {
    return call_tpm_service_interval;
  }
  public String getError_code()
  {
    return error_code;
  }
  public void setError_code(String error_code)
  {
    this.error_code = error_code;
  }
  public void setCall_tpme_service_interval(String call_tpme_service_interval)
  {
    this.call_tpm_service_interval = call_tpme_service_interval;
  }
  public void setCall_tpme_interval(String call_tpme_interval)
  {
    this.call_tpme_interval = call_tpme_interval;
  }
  public void setCall_tpm_stf_out_time(String call_tpm_stf_out_time)
  {
    this.call_tpm_stf_out_time = call_tpm_stf_out_time;
  }
  public void setCall_tpm_stf_in_time(String call_tpm_stf_in_time)
  {
    this.call_tpm_stf_in_time = call_tpm_stf_in_time;
  }
  public void setCall_tpm_out_time(String call_tpm_out_time)
  {
    this.call_tpm_out_time = call_tpm_out_time;
  }
  public void setCall_tpm_in_time(String call_tpm_in_time)
  {
    this.call_tpm_in_time = call_tpm_in_time;
  }
  public void setCall_tpm_etf_out_time(String call_tpm_etf_out_time)
  {
    this.call_tpm_etf_out_time = call_tpm_etf_out_time;
  }
  public void setCall_tpm_etf_in_time(String call_tpm_etf_in_time)
  {
    this.call_tpm_etf_in_time = call_tpm_etf_in_time;
  }
  public void setCall_service_name(String call_service_name)
  {
    this.call_service_name = call_service_name;
  }
  public void setCall_orgseq(String call_orgseq)
  {
    this.call_orgseq = call_orgseq;
  }
  public void setCall_location(String call_location)
  {
    this.call_location = call_location;
  }
  public void setCall_hostseq(String call_hostseq)
  {
    this.call_hostseq = call_hostseq;
  }
  public char getOffset()
  {
    return offset;
  }
  public void setOffset(char offset)
  {
    this.offset = offset;
  }


}

