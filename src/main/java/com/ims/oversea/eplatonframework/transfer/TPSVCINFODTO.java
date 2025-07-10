package com.ims.oversea.eplatonframework.transfer;

import java.util.*;

import com.chb.coses.framework.transfer.*;
import com.ims.oversea.eplatonframework.transfer.TPMSVCINFO;
import com.ims.oversea.foundation.utility.*;
import com.ims.oversea.foundation.logej.*;
import com.ims.oversea.framework.transaction.constant.TCFConstants;

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


public class TPSVCINFODTO extends DTO
{
  private  HashMap tpmsvcinfolist;
  private String reqName;
  private String system_name;    // CashCard
                                 // EPlatonBizDelegateSB에서
  private String operation_name; // COMMO1000 , DED0021000
                                 // SYS(DED)+ROC(0202)+PGNO(1000)
  private String operation_method;
  private String cdto_name;
  private String action_name;
  // 클라이언트에서 셋팅 정보
  // "com.ims.oversea.eplatonframework.business.delegate.action.CashCardBizAction"
  // EPlatonBizDelegateSB에서 각 업무 대표시스템을 호출하기 위한 것이다
  // 이 로직은 기존의 방식을 고수한다.
  // CashCard 시스템의경우
  // step 1. eplatonFramework/business/delegate/action/CashCardBizAction 구현
  // step 2. EPlatonBizDelegateSB에서 TPSVCINFODTO.action_name을 구하고,
  //         EPlatonBizAction action = (EPlatonBizAction)(Class.forName(actionClassName).newInstance());
  //         대표시스템을 호출한다.
  //         각 업무시스템의 action(CashCardBizAction)은 doAct()메소드를 구현한다.
  // step 3. doAct()에서는 action클래스에서 생성된 세션빈과 파라미터(EPlatonEvent)를 셋팅해서 각 업무시스템을 호출한다.
  // step 4. facade단의 대표업무시스템(CashCardManagementSB)의 메소드 execute()호출된다
  //         execute()메소드안에서 TCF 클래스를 호출하는 구조로 되어 있다.
  // step 5. TCF 모듈에서 BTF를 호출해 업무단의 operationBizAction을 호출한다.
  //         상위의 모든 과정에서 알수 있듯이, 기존의 시스템에서는 CDTO을 가지고 직접호출하는 구조로 되어 있어
  //         ejb-client속에 각종 인터페이스와 CDTO을 묶는 구조로 가지고 갔으나, 새시스템 구조에서는 공통인터페이스를
  //         각 업무단의 OPERATION까지 전달해 각 업무의 EJB-JAR만 재부팅하면 되는 구조로 바꾸었으므로
  //         기존에 웹로직을 재기동하는 것을 없애는 방향으로 설계한다.

  private String hostseq;
  private String orgseq;
  private String tx_timer;
  private String tpfq;
  private String errorcode;
  private String trclass; //마감전후구분
  private String bp_sequence;
  private String web_timeout;
  private String web_intime;
  private String web_outtime;
  private String systemInTime;
  private String systemOutTime;
  private String system_date;
  private String error_message;
  private String logic_level;
  private String STF_intime="XXXXXXXX";
  private String STF_outtime="XXXXXXXX";
  private String STF_interval="000";
  private String BTF_intime="XXXXXXXX";
  private String BTF_outtime="XXXXXXXX";
  private String BTF_interval="000";
  private String ETF_intime="XXXXXXXX";
  private String ETF_outtime="XXXXXXXX";
  private String ETF_interval="000";
  private long   ldumy01=0;
  private String sdumy01="*";


  public TPSVCINFODTO()
  {
    reqName="*";
    system_name="*";
    operation_name="*";
    operation_method="*";
    cdto_name="*";
    action_name="*";
    hostseq="*";
    orgseq="*";
    tx_timer=TCFConstants.TCF_TRANSACTION_DEFAULT_TIMEOUT;
    tpfq="*";
    errorcode=TCFConstants.TCF_SUCCESS_ERRCODE;
    trclass="*";
    web_timeout=TCFConstants.TCF_TRANSACTION_DEFAULT_TIMEOUT;
    web_intime="*";
    web_outtime="*";
    systemInTime="*";
    systemOutTime="*";
    system_date=CommonUtil.GetSysDate();
    error_message="*";
    logic_level="*";
    bp_sequence=CommonUtil.GetSysTime();
  }

  public String getCdto_name()
  {
    return cdto_name;
  }
  public String getLogic_level()
  {
    return logic_level;
  }

  public String getErrorcode()
  {
    return errorcode;
  }
  public String getHostseq()
  {
    return hostseq;
  }
  public String getOperation_name()
  {
    return operation_name;
  }
  public String getOrgseq()
  {
    return orgseq;
  }
  public String getSystem_date()
  {
    return system_date;
  }
  public String getSystem_name()
  {
    return system_name;
  }
  public String getSystemInTime()
  {
    return systemInTime;
  }
  public String getSystemOutTime()
  {
    return systemOutTime;
  }
  public String getTpfq()
  {
    return tpfq;
  }
  public HashMap getTpmsvcinfolist()
  {
    return tpmsvcinfolist;
  }
  public String getTrclass()
  {
    return trclass;
  }
  public String getTx_timer()
  {
    return tx_timer;
  }
  public String getWeb_intime()
  {
    return web_intime;
  }
  public String getWeb_outtime()
  {
    return web_outtime;
  }
  public String getWeb_timeout()
  {
    return web_timeout;
  }

  public String getError_message()
  {
    return error_message;
  }

  public void setWeb_timeout(String web_timeout)
  {
    this.web_timeout = web_timeout;
  }
  public void setWeb_outtime(String web_outtime)
  {
    this.web_outtime = web_outtime;
  }
  public void setWeb_intime(String web_intime)
  {
    this.web_intime = web_intime;
  }
  public void setTx_timer(String tx_timer)
  {
    this.tx_timer = tx_timer;
  }
  public void setTrclass(String trclass)
  {
    this.trclass = trclass;
  }
  public void setTpmsvcinfolist(HashMap tpmsvcinfolist)
  {
    this.tpmsvcinfolist = tpmsvcinfolist;
  }
  public void setTpfq(String tpfq)
  {
    this.tpfq = tpfq;
  }
  public void setSystemOutTime(String systemOutTime)
  {
    this.systemOutTime = systemOutTime;
  }
  public void setSystemInTime(String systemInTime)
  {
    this.systemInTime = systemInTime;
  }
  public void setSystem_name(String system_name)
  {
    this.system_name = system_name;
  }
  public void setSystem_date(String system_date)
  {
    this.system_date = system_date;
  }
  public void setOrgseq(String orgseq)
  {
    this.orgseq = orgseq;
  }
  public void setOperation_name(String operation_name)
  {
    this.operation_name = operation_name;
  }
  public void setHostseq(String hostseq)
  {
    this.hostseq = hostseq;
  }
  public void setErrorcode(String errorcode)
  {
    this.errorcode = errorcode;
  }
  public void setError_message(String error_message)
  {
    this.error_message = error_message;
  }
  public void setCdto_name(String cdto_name)
  {
    this.cdto_name = cdto_name;
  }

  public ArrayList getAllTPMSVCINFO()
  {
    if( tpmsvcinfolist == null )
      tpmsvcinfolist = new HashMap();

    Set set = tpmsvcinfolist.keySet();
    Iterator it = set.iterator();

    ArrayList al = new ArrayList();
    while(it.hasNext()) {
      Object obj = tpmsvcinfolist.get(it.next());
      //System.out.println("---------:" + obj.getClass().getName());
      TPMSVCINFO hs = (TPMSVCINFO) obj;
      al.add(hs);
    }
    return al;
  }

  /**
   *	로그인한 전체 사용자 수를 반환하는 메서드
   *
   *	@return 로그인한 전체 사용자 수
   */
  public int getTPMSVCINFOTotalCount()
  {
    return tpmsvcinfolist.size();
  }

  /**
   *	SessionManager가 저장하고 있는 TPMSVCINFO object의 collection을 반환하는 메서드
   *
   *	@return TPMSVCINFO object의 collection
   */
  public ArrayList getTotalTPMSVCINFO()
  {
    if( tpmsvcinfolist == (HashMap) null )
      tpmsvcinfolist = new HashMap();

    Set set = tpmsvcinfolist.entrySet();
    ArrayList al = new ArrayList(set);
    return al;
  }

  public ArrayList getTotalTPMSVCINFO(int kk)
  {
    if( tpmsvcinfolist == (HashMap) null )
      tpmsvcinfolist = new HashMap();

    ArrayList al = (ArrayList)this.tpmsvcinfolist.values();
    return al;
  }

  public static void prnttpmsvcinfo(EPlatonEvent event)
  {
    ArrayList al = event.getTPSVCINFODTO().getTotalTPMSVCINFO();
    for( int i = 0 ; i < al.size() ; i ++ )
    {
      TPMSVCINFO tm = (TPMSVCINFO)al.get(i);
      LOGEJ.getInstance().printf(1,event,
                                 "| " + i + " " + tm.getCall_service_name()
                                 + " " + tm.getCall_tpm_in_time()
                                 + " " + tm.getCall_tpm_out_time()
                                 + " " + tm.getError_code()
                                 + " " + "010" + " " + "LOC"  );
    }
  }

  public TPMSVCINFO removeTPMSVCINFO(String key)
  {
    return (TPMSVCINFO)tpmsvcinfolist.remove(key);
  }

  public TPMSVCINFO gettpmsvcinfo(String key)
  {
    return (TPMSVCINFO)tpmsvcinfolist.get(key);
  }

  public boolean addtpmsvcinfo(TPMSVCINFO tpmsvcinfo){
    try{
      TPMSVCINFO ctpm = null;
      String key = null;

      if( tpmsvcinfolist == (HashMap) null )
        tpmsvcinfolist = new HashMap();

      if( tpmsvcinfo == null )
        return false;
      else
        key = tpmsvcinfo.getCall_service_name()+"."+tpmsvcinfo.getCall_tpm_in_time();

      if( tpmsvcinfolist.containsKey(key) ){
         ctpm = (TPMSVCINFO)tpmsvcinfolist.get(key);
         ctpm.setCall_hostseq(tpmsvcinfo.getCall_hostseq());
         ctpm.setCall_location(tpmsvcinfo.getCall_location());
         ctpm.setCall_orgseq(tpmsvcinfo.getCall_orgseq());
         ctpm.setCall_service_name(tpmsvcinfo.getCall_service_name());
         ctpm.setCall_tpm_etf_in_time(tpmsvcinfo.getCall_tpm_etf_in_time());
         ctpm.setCall_tpm_etf_out_time(tpmsvcinfo.getCall_tpm_etf_out_time());
         ctpm.setCall_tpm_in_time(tpmsvcinfo.getCall_tpm_in_time());
         ctpm.setCall_tpm_out_time(tpmsvcinfo.getCall_tpm_out_time());
         ctpm.setCall_tpm_stf_in_time(tpmsvcinfo.getCall_tpm_stf_in_time());
         ctpm.setCall_tpm_stf_out_time(tpmsvcinfo.getCall_tpm_stf_out_time());
         ctpm.setCall_tpm_etf_in_time(tpmsvcinfo.getCall_tpm_etf_in_time());
         ctpm.setCall_tpm_etf_out_time(tpmsvcinfo.getCall_tpm_etf_out_time());
         ctpm.setCall_tpm_interval(tpmsvcinfo.getCall_tpm_interval());
         ctpm.setCall_tpm_service_interval(tpmsvcinfo.getCall_tpm_service_interval());
         ctpm.setError_code(tpmsvcinfo.getError_code());
         ctpm.setOffset('O');
      }
      else{
        tpmsvcinfolist.put(key,tpmsvcinfo);
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public String getAction_name()
  {
    return action_name;
  }
  public void setAction_name(String action_name)
  {
    this.action_name = action_name;
  }

  public void setLogic_level(String logic_level)
  {
    this.logic_level = logic_level;
  }
  public String getBTF_intime()
  {
    return BTF_intime;
  }
  public String getBTF_outtime()
  {
    return BTF_outtime;
  }
  public String getETF_intime()
  {
    return ETF_intime;
  }
  public String getETF_outtime()
  {
    return ETF_outtime;
  }
  public String getSTF_intime()
  {
    return STF_intime;
  }
  public String getSTF_outtime()
  {
    return STF_outtime;
  }
  public void setSTF_outtime(String STF_outtime)
  {
    this.STF_outtime = STF_outtime;
  }
  public void setSTF_intime(String STF_intime)
  {
    this.STF_intime = STF_intime;
  }
  public void setETF_outtime(String ETF_outtime)
  {
    this.ETF_outtime = ETF_outtime;
  }
  public void setETF_intime(String ETF_intime)
  {
    this.ETF_intime = ETF_intime;
  }
  public void setBTF_outtime(String BTF_outtime)
  {
    this.BTF_outtime = BTF_outtime;
  }
  public void setBTF_intime(String BTF_intime)
  {
    this.BTF_intime = BTF_intime;
  }
  public String getOperation_method()
  {
    return operation_method;
  }
  public void setOperation_method(String operation_method)
  {
    this.operation_method = operation_method;
  }
  public String getReqName() {
    return reqName;
  }
  public void setReqName(String reqName) {
    this.reqName = reqName;
  }
  public String getBp_sequence() {
    return bp_sequence;
  }
  public void setBp_sequence(String bp_sequence) {
    this.bp_sequence = bp_sequence;
  }
  public String getBTF_interval() {
    return BTF_interval;
  }
  public void setBTF_interval(String BTF_interval) {
    this.BTF_interval = BTF_interval;
  }
  public String getETF_interval() {
    return ETF_interval;
  }
  public void setETF_interval(String ETF_interval) {
    this.ETF_interval = ETF_interval;
  }
  public String getSTF_interval() {
    return STF_interval;
  }
  public void setSTF_interval(String STF_interval) {
    this.STF_interval = STF_interval;
  }
  public long getLdumy01() {
    return ldumy01;
  }
  public void setLdumy01(long ldumy01) {
    this.ldumy01 = ldumy01;
  }
  public String getSdumy01() {
    return sdumy01;
  }
  public void setSdumy01(String sdumy01) {
    this.sdumy01 = sdumy01;
  }

}


