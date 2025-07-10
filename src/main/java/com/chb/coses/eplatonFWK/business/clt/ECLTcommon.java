package com.chb.coses.eplatonFWK.business.clt;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.chb.coses.eplatonFWK.transfer.*;
import com.chb.coses.eplatonFWK.business.helper.TPCsendrecv;
import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.chb.coses.eplatonFWK.business.helper.CommonUtil;

public class ECLTcommon {
  public TPCsendrecv tpcsendrecv;

  public ECLTcommon() throws Exception{
    this.tpcsendrecv = new TPCsendrecv("21.101.3.47","7001");
  }

  public EPlatonEvent doSTF(EPlatonEvent event)
  {
    EPlatonCommonDTO commonDTO=null;
    TPSVCINFODTO tpsvcinfo=null;

    try{
      commonDTO = (EPlatonCommonDTO)event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      commonDTO.setBankCode("11");
      commonDTO.setChannelType("03");
      commonDTO.setBranchCode("99");
      commonDTO.setEventNo("12345678");
      commonDTO.setSystemDate("20031031");
      commonDTO.setUserID("wsjang");
      commonDTO.setReqName("com.chb.coses.eplatonFWK.business.delegate.action.CashCardBizAction");
      commonDTO.setgetIPAddress("172.21.111.110");

      tpsvcinfo.setSystem_name("CashCard");
      tpsvcinfo.setAction_name("com.chb.coses.eplatonFWK.business.delegate.action.CashCardBizAction");
      tpsvcinfo.setOperation_name("com.chb.coses.eplatonFWK.othersystem.operation.COMMO1000");
      tpsvcinfo.setOperation_method("act");
      tpsvcinfo.setCdto_name("com.chb.coses.CashCard.transfer.CashCardCDTO");
      tpsvcinfo.setTpfq("200");
      tpsvcinfo.setErrorcode("IZZ000");
      tpsvcinfo.setTx_timer("030");
    }
    catch(Exception e) {
      e.printStackTrace();
      System.out.println(e.toString());
      tpsvcinfo.setErrorcode("ETPC001");
      tpsvcinfo.setError_message("TPCsendrecv error");
    }
    finally{
      return event;
    }
  }

  public EPlatonEvent execute(EPlatonEvent event) throws Exception
  {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPlatonEvent resevent = null;
    try{
      commonDTO = (EPlatonCommonDTO)event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();

      resevent = doSTF(event);
      System.out.println("SEND-"+com.chb.coses.foundation.utility.Reflector.objectToString(resevent));

      switch( resevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'E':
          break;
        case 'I':
          resevent = (EPlatonEvent)tpcsendrecv.callEJB("CashCard",
              "CashCardBizAction",
              "com.chb.coses.eplatonFWK.othersystem.operation.COMMO1000",
              event);
          break;
      }

      resevent = doETF(resevent);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new Exception(e.getMessage()) ;
    }

    return resevent;
  }

  public EPlatonEvent doETF(EPlatonEvent event)
  {
    try
    {
    }
    catch(Exception e)
    {
      e.printStackTrace();
      System.out.println(e.toString());
    }
    return event;
  }

  public static void main(String args[]) throws Exception
  {
    try{

      int cnt=0;
      while(true){
        cnt++;
        if( cnt == CommonUtil.Str2Int(args[0]) ) break;
        System.out.println("----------------------------------거래를 시작합니다["+cnt+"]");
        System.out.println("--기본정보를 저장");
        try{
          AccountQueryCDTO acdto = new AccountQueryCDTO();
          acdto.setAccountNumber("0001100100000048");
          acdto.setBankCode("03");

          System.out.println("--------EJB 호출시작");
          EPlatonEvent event = new EPlatonEvent();
          event.setRequest(acdto);
          ECLTcommon clt = new ECLTcommon();
          event = clt.doSTF(event);
          event = clt.execute(event);
          System.out.println("--------EJB 호출종료");

          if( event.getTPSVCINFODTO().getErrorcode().equals ("IZZ000") ){
            System.out.println("거래가 성공적으로 호출되었습니다:"+event.getTPSVCINFODTO().getErrorcode());
            AccountQueryCDTO result = (AccountQueryCDTO)event.getResponse();
            System.out.println("************************************************");
            System.out.println("***AccountQueryCDTO***"+result.toString());
            System.out.println("--txno:"+event.getTPSVCINFODTO().getHostseq());
            System.out.println("--amount:"+acdto.getCollectedBalance());
            System.out.println("************************************************");
          }
          else
            System.out.println("거래호출이 에러가 발생했습니다.");

          System.out.println("--기본정보를 재저장하고 성공/실패유무를 판단");
          System.out.println("----------------------------------거래를 종료합니다");
        }
        catch(Exception ex){
          ex.printStackTrace();
        }

      }

      return ;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}

