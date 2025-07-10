package com.chb.coses.eplatonFramework.transfer;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.util.ArrayList;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.eplatonFramework.transfer.TPMSVCINFO;

public class TPSVCINFODTO extends DTO
{
  private  ArrayList tpmsvcinfolist;
  private String system_name;    // CashCard
                                 // EPlatonBizDelegateSB에서
  private String operation_name; // COMMO1000 , DED0021000
                                 // SYS(DED)+ROC(0202)+PGNO(1000)
  private String cdto_name;
  private String action_name;
  // 클라이언트에서 셋팅 정보
  // "com.chb.coses.eplatonFramework.business.delegate.action.CashCardBizAction"
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
  private String web_timeout;
  private String web_intime;
  private String web_outtime;
  private String systemInTime;
  private String systemOutTime;
  private String system_date;
  private String error_message;


  public String getCdto_name()
  {
    return cdto_name;
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
  public ArrayList getTpmsvcinfolist()
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
  public void setTpmsvcinfolist(ArrayList tpmsvcinfolist)
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
  public void addTpmSVCInfo(TPMSVCINFO tpmsvcinfo) throws Exception {
    if( this.tpmsvcinfolist == (ArrayList) null )
      this.tpmsvcinfolist = new ArrayList();
    this.tpmsvcinfolist.add(tpmsvcinfo);
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


