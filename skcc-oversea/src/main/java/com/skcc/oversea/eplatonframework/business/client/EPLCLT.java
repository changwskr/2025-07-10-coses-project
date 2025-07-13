package com.skcc.oversea.eplatonframework.business.client;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.skcc.oversea.eplatonframework.transfer.EPLcommonCDTO;
import com.skcc.oversea.eplatonframework.transfer.*;
import com.skcc.oversea.framework.transaction.tpmutil.TPCsendrecv;
import com.skcc.oversea.foundation.utility.CommonUtil;

public class EPLCLT
{
  public TPCsendrecv tpcsendrecv;

  public EPLCLT() throws Exception{
  }

  public EPlatonEvent execute(EPlatonEvent event,String call_request_name) throws Exception
  {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPlatonEvent resevent = null;

    try{
      commonDTO = (EPlatonCommonDTO)event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();

      commonDTO.setBankCode("11");
      commonDTO.setChannelType("03");
      commonDTO.setBranchCode("99");
      commonDTO.setEventNo("12345678");
      commonDTO.setSystemDate("20031031");
      commonDTO.setUserID("wsjang");
      commonDTO.setgetIPAddress("172.21.111.110");
      tpsvcinfo.setBp_sequence(CommonUtil.GetSysTime() );

      System.out.println("SEND-"+
                         com.chb.coses.foundation.utility.Reflector.objectToString(event));

      resevent = (EPlatonEvent)
                 TPCsendrecv.getInstance("21.101.3.47","7001").callEJB(
                 call_request_name,"30",event);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new Exception(e.getMessage()) ;
    }

    return resevent;
  }


  public static void main(String args[]) throws Exception
  {
    System.out.println("java com.kdb.oversea.eplatonframework.business.client.EPLCLT 건수 lcommon-callmethod03");

    try{

      int cnt=0;

      while(true)
      {
        cnt++;
        if( cnt == CommonUtil.Str2Int(args[0]) )
          break;


        System.out.println("----------------------------------거래를 시작합니다["+cnt+"]");
        System.out.println("--기본정보를 저장");

        try{
          EPLcommonCDTO acdto = new EPLcommonCDTO();
          acdto.setAccountNumber("0001100100000048");
          acdto.setBankCode("03");

          System.out.println("--------EJB 호출시작");
          EPlatonEvent event = new EPlatonEvent();
          event.setRequest(acdto);

          EPLCLT clt = new EPLCLT();
          event = clt.execute(event,args[1]);
          System.out.println("--------EJB 호출종료");

          if( event.getTPSVCINFODTO().getErrorcode().equals ("IZZ000") ){
            System.out.println("거래가 성공적으로 호출되었습니다:"+event.getTPSVCINFODTO().getErrorcode());
            EPLcommonCDTO result = (EPLcommonCDTO)event.getResponse();
            System.out.println("************************************************");
            System.out.println("***EPLcommonCDTO***"+result.toString());
            System.out.println("--txno:"+event.getTPSVCINFODTO().getHostseq());
            System.out.println("--amount:"+acdto.getCollectedBalance());
            System.out.println("************************************************");
          }
          else {
            System.out.println("거래호출이 에러가 발생했습니다." + event.getTPSVCINFODTO().getErrorcode());
          }

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



