package com.skcc.oversea.eplatonframework.transfer;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.transfer.TPSVCINFODTO;
import com.skcc.oversea.foundation.utility.*;
import com.skcc.oversea.foundation.logej.*;
import com.skcc.oversea.framework.transfer.*;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *
 * //TPSsendrecv 시작종료시간
 * call_tpm_in_time;
 * call_tpm_out_time;
 * call_tpm_interval;
 * 
 * //상대방 호출머신의 STF시작종료시간-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
 * call_tpm_stf_in_time;
 * call_tpm_stf_out_time;
 * 
 * //상대방 호출머신의 ETF시작종료시간-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
 * call_tpm_etf_in_time;
 * call_tpm_etf_out_time;
 * 
 * //상대방 호출머신의 STF에서ETF에까지의 간격
 * call_tpm_service_interval;
 * 
 * //상대방 시스템의 시퀀스-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
 * call_hostseq;
 * call_orgseq;
 * 
 * //상대방 시스템의 에러코드-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
 * error_code;
 * 
 * //상대방 시스템의 리턴한 위치를 보여준다.-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
 * call_location;
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

public class TPMSVCINFO extends DTO {

  private char offset = 'X';
  private String call_service_name;

  // TPSsendrecv 시작종료시간
  private String call_tpm_in_time;
  private String call_tpm_out_time;
  private String call_tpm_interval;
  // 상대방 호출머신의 STF시작종료시간-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
  private String call_tpm_stf_in_time;
  private String call_tpm_stf_out_time;
  // 상대방 호출머신의 ETF시작종료시간-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
  private String call_tpm_etf_in_time;
  private String call_tpm_etf_out_time;
  // 상대방 호출머신의 STF에서ETF에까지의 간격
  private String call_tpm_service_interval;
  // 상대방 시스템의 시퀀스-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
  private String call_hostseq;
  private String call_orgseq;
  // 상대방 시스템의 에러코드-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
  private String error_code;
  // 상대방 시스템의 리턴한 위치를 보여준다.-이값은 상대방 시스템에서 리턴한 EPlatonEvent객체로부터가지고온다
  private String call_location;

  public TPMSVCINFO(String tarsystem, EPlatonEvent event) {
    TPSVCINFODTO tpmdto = event.getTPSVCINFODTO();

    this.setCall_hostseq(tpmdto.getHostseq()); // 상대방머신의 hostseq의 값을 셋팅
                                               // TPCsendrecv이후 가지고 오는 eplatonevent객체의 값을 셋팅
    this.setCall_orgseq(tpmdto.getOrgseq()); // 상대방머신의 orgseq의 값을 셋팅한다.
                                             // TPCsendrecv이후 가지고 오는 eplatonevent객체의 값을 셋팅
    this.setCall_service_name(tarsystem); // 호출할 상대방 머신의 시스템명을 명시한다.여기서 이값은 request_name이다
                                          // 이 값은 TPCsendrecv에 전달되는 requestname이다.
    this.setCall_tpm_in_time(CommonUtil.GetSysTime()); // TPSsendrecv시작시간
    this.setCall_tpm_out_time(this.getCall_tpm_in_time()); // TPSsendrecv종료시간

    this.setError_code("IZZ000"); // TPCsendrecv시 상대방에러코드
    this.setOffset('0'); // TPCsendrecv 시작 flag

  }

  public String getCall_hostseq() {
    return call_hostseq;
  }

  public String getCall_location() {
    return call_location;
  }

  public String getCall_orgseq() {
    return call_orgseq;
  }

  public String getCall_service_name() {
    return call_service_name;
  }

  public String getCall_tpm_etf_in_time() {
    return call_tpm_etf_in_time;
  }

  public String getCall_tpm_etf_out_time() {
    return call_tpm_etf_out_time;
  }

  public String getCall_tpm_in_time() {
    return call_tpm_in_time;
  }

  public String getCall_tpm_out_time() {
    return call_tpm_out_time;
  }

  public String getCall_tpm_stf_in_time() {
    return call_tpm_stf_in_time;
  }

  public String getCall_tpm_stf_out_time() {
    return call_tpm_stf_out_time;
  }

  public String getCall_tpme_interval() {
    return call_tpm_interval;
  }

  public String getCall_tpme_service_interval() {
    return call_tpm_service_interval;
  }

  public String getError_code() {
    return error_code;
  }

  public void setError_code(String error_code) {
    this.error_code = error_code;
  }

  public void setCall_tpme_service_interval(String call_tpme_service_interval) {
    this.call_tpm_service_interval = call_tpme_service_interval;
  }

  public void setCall_tpme_interval(String call_tpme_interval) {
    this.call_tpm_interval = call_tpme_interval;
  }

  public void setCall_tpm_stf_out_time(String call_tpm_stf_out_time) {
    this.call_tpm_stf_out_time = call_tpm_stf_out_time;
  }

  public void setCall_tpm_stf_in_time(String call_tpm_stf_in_time) {
    this.call_tpm_stf_in_time = call_tpm_stf_in_time;
  }

  public void setCall_tpm_out_time(String call_tpm_out_time) {
    this.call_tpm_out_time = call_tpm_out_time;
    //////////////////////////////////////////////////////////////////////////
    // INTERVAL 강제 셋팅 나중 제셋팅
    /////////////////////////////////////////////////////////////////////////
    this.setCall_tpme_interval("010");
  }

  public void setCall_tpm_in_time(String call_tpm_in_time) {
    this.call_tpm_in_time = call_tpm_in_time;
  }

  public void setCall_tpm_etf_out_time(String call_tpm_etf_out_time) {
    this.call_tpm_etf_out_time = call_tpm_etf_out_time;
  }

  public void setCall_tpm_etf_in_time(String call_tpm_etf_in_time) {
    this.call_tpm_etf_in_time = call_tpm_etf_in_time;
  }

  public void setCall_service_name(String call_service_name) {
    this.call_service_name = call_service_name;
  }

  public void setCall_orgseq(String call_orgseq) {
    this.call_orgseq = call_orgseq;
  }

  public void setCall_location(String call_location) {
    this.call_location = call_location;
  }

  public void setCall_hostseq(String call_hostseq) {
    this.call_hostseq = call_hostseq;
  }

  public char getOffset() {
    return offset;
  }

  public void setOffset(char offset) {
    this.offset = offset;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TPMSVCINFO other = (TPMSVCINFO) obj;
    if (call_hostseq == null) {
      if (other.call_hostseq != null)
        return false;
    } else if (!call_hostseq.equals(other.call_hostseq))
      return false;
    if (call_location == null) {
      if (other.call_location != null)
        return false;
    } else if (!call_location.equals(other.call_location))
      return false;
    if (call_orgseq == null) {
      if (other.call_orgseq != null)
        return false;
    } else if (!call_orgseq.equals(other.call_orgseq))
      return false;
    if (call_service_name == null) {
      if (other.call_service_name != null)
        return false;
    } else if (!call_service_name.equals(other.call_service_name))
      return false;
    if (call_tpm_etf_in_time == null) {
      if (other.call_tpm_etf_in_time != null)
        return false;
    } else if (!call_tpm_etf_in_time.equals(other.call_tpm_etf_in_time))
      return false;
    if (call_tpm_etf_out_time == null) {
      if (other.call_tpm_etf_out_time != null)
        return false;
    } else if (!call_tpm_etf_out_time.equals(other.call_tpm_etf_out_time))
      return false;
    if (call_tpm_in_time == null) {
      if (other.call_tpm_in_time != null)
        return false;
    } else if (!call_tpm_in_time.equals(other.call_tpm_in_time))
      return false;
    if (call_tpm_interval == null) {
      if (other.call_tpm_interval != null)
        return false;
    } else if (!call_tpm_interval.equals(other.call_tpm_interval))
      return false;
    if (call_tpm_out_time == null) {
      if (other.call_tpm_out_time != null)
        return false;
    } else if (!call_tpm_out_time.equals(other.call_tpm_out_time))
      return false;
    if (call_tpm_service_interval == null) {
      if (other.call_tpm_service_interval != null)
        return false;
    } else if (!call_tpm_service_interval.equals(other.call_tpm_service_interval))
      return false;
    if (call_tpm_stf_in_time == null) {
      if (other.call_tpm_stf_in_time != null)
        return false;
    } else if (!call_tpm_stf_in_time.equals(other.call_tpm_stf_in_time))
      return false;
    if (call_tpm_stf_out_time == null) {
      if (other.call_tpm_stf_out_time != null)
        return false;
    } else if (!call_tpm_stf_out_time.equals(other.call_tpm_stf_out_time))
      return false;
    if (error_code == null) {
      if (other.error_code != null)
        return false;
    } else if (!error_code.equals(other.error_code))
      return false;
    if (offset != other.offset)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((call_hostseq == null) ? 0 : call_hostseq.hashCode());
    result = prime * result + ((call_location == null) ? 0 : call_location.hashCode());
    result = prime * result + ((call_orgseq == null) ? 0 : call_orgseq.hashCode());
    result = prime * result + ((call_service_name == null) ? 0 : call_service_name.hashCode());
    result = prime * result + ((call_tpm_etf_in_time == null) ? 0 : call_tpm_etf_in_time.hashCode());
    result = prime * result + ((call_tpm_etf_out_time == null) ? 0 : call_tpm_etf_out_time.hashCode());
    result = prime * result + ((call_tpm_in_time == null) ? 0 : call_tpm_in_time.hashCode());
    result = prime * result + ((call_tpm_interval == null) ? 0 : call_tpm_interval.hashCode());
    result = prime * result + ((call_tpm_out_time == null) ? 0 : call_tpm_out_time.hashCode());
    result = prime * result + ((call_tpm_service_interval == null) ? 0 : call_tpm_service_interval.hashCode());
    result = prime * result + ((call_tpm_stf_in_time == null) ? 0 : call_tpm_stf_in_time.hashCode());
    result = prime * result + ((call_tpm_stf_out_time == null) ? 0 : call_tpm_stf_out_time.hashCode());
    result = prime * result + ((error_code == null) ? 0 : error_code.hashCode());
    result = prime * result + offset;
    return result;
  }

  @Override
  public String toString() {
    return "TPMSVCINFO{" +
        "offset=" + offset +
        ", call_service_name='" + call_service_name + '\'' +
        ", call_tpm_in_time='" + call_tpm_in_time + '\'' +
        ", call_tpm_out_time='" + call_tpm_out_time + '\'' +
        ", call_tpm_interval='" + call_tpm_interval + '\'' +
        ", call_tpm_stf_in_time='" + call_tpm_stf_in_time + '\'' +
        ", call_tpm_stf_out_time='" + call_tpm_stf_out_time + '\'' +
        ", call_tpm_etf_in_time='" + call_tpm_etf_in_time + '\'' +
        ", call_tpm_etf_out_time='" + call_tpm_etf_out_time + '\'' +
        ", call_tpm_service_interval='" + call_tpm_service_interval + '\'' +
        ", call_hostseq='" + call_hostseq + '\'' +
        ", call_orgseq='" + call_orgseq + '\'' +
        ", error_code='" + error_code + '\'' +
        ", call_location='" + call_location + '\'' +
        '}';
  }

  @Override
  public DTO clone() {
    try {
      return (DTO) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException("Clone not supported", e);
    }
  }
}
