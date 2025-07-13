package com.skcc.oversea.common.business.facade;

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;
import java.math.BigDecimal;

import com.skcc.oversea.framework.exception.CosesAppException;
import com.skcc.oversea.deposit.business.facade.*;
import com.skcc.oversea.deposit.transfer.*;
import com.skcc.oversea.framework.transaction.helper.Convert;
import com.skcc.oversea.foundation.logej.*;
import com.skcc.oversea.framework.transaction.tpmutil.TPMSVCAPI;
import com.skcc.oversea.framework.transaction.tpmutil.TPSsendrecv;
import com.skcc.oversea.eplatonframework.transfer.*;
import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.skcc.oversea.eplatonframework.transfer.EPLcommonCDTO;

public class CommonManagementSBean implements ICommonManagementSB{

    public void ejbCreate() throws CreateException {
        /**@todo Complete this method*/
    }

    public EPlatonEvent callmethod01(EPlatonEvent event)
        throws CosesAppException
    {
      EPlatonEvent     response_event = Convert.createEPlatonEvent(event) ;
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      EPLcommonCDTO rescdto = null;
      EPLcommonCDTO reqcdto = new EPLcommonCDTO() ;

      try{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod01 START]");

        commonDTO = (EPlatonCommonDTO)event.getCommon();
        tpsvcinfo = event.getTPSVCINFODTO();
        rescdto = (EPLcommonCDTO)event.getRequest();

        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"신규계좌에 대한 정보를 셋팅한다");
        reqcdto.setAccountNumber("0001100100000048");
        reqcdto.setBankCode("03");
        reqcdto.setAccountNumber("9999999999999999");

        ////////////////////////////////////////////////////////////////////////////
        // TPSsendrecv TEST
        ////////////////////////////////////////////////////////////////////////////
        EPlatonCommonDTO tpmcommonDTO = event.getCommon() ;
        TPSVCINFODTO tpmtpsvcinfoDTO = event.getTPSVCINFODTO() ;
        response_event = TPSsendrecv.getInstance().CallEJB("spcashcard-callmethod01",event,reqcdto);
        //response_event = TPSsendrecv.getInstance().CallEJB("cashCard-callmethod01",event,reqcdto);
        //response_event = TPSsendrecv.getInstance().CallEJB("cashCard-callmethod02",event,reqcdto);
        //response_event = TPSsendrecv.getInstance().CallEJB("deposit-callmethod03",event,reqcdto);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"3333333333333");
        ////////////////////////////////////////////////////////////////////////////

        switch( response_event.getTPSVCINFODTO().getErrorcode().charAt(0) )
        {
          case 'I' :
            response_event.setResponse(response_event.getResponse() );
            break;
          case 'E' :
            response_event.getTPSVCINFODTO().setErrorcode(response_event.getTPSVCINFODTO().getErrorcode() );
            String errorcode = "EFWK0047"+"|"+response_event.getTPSVCINFODTO().getErrorcode();
            tpsvcinfo.setErrorcode(errorcode);
            tpsvcinfo.setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
            break;
        }

        if( response_event.getResponse() == null )
        {
          LOGEJ.getInstance().printf(1,event,"response cdto null");
        }
        else{
          LOGEJ.getInstance().printf(1,event,"response cdto not null");
        }
      }
      catch(Exception ex){
        ex.printStackTrace();
        String errorcode = "EFWK0048"+"|"+tpsvcinfo.getErrorcode();
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
        LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
      }
      ////////////////////////////////////////////////////////////////////////////

      //상대시스템에서 보내준 CDTO을 관리하고 클라이언트로 넘긴다.
      event.setResponse(response_event.getResponse() );



      ////////////////////////////////////////////////////////////////////////////

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod01  END]"+event.getTPSVCINFODTO().getSystem_name()  );
      return event;

    }

    public EPlatonEvent callmethod02(EPlatonEvent event) throws CosesAppException
    {
      EPlatonEvent     response_event = Convert.createEPlatonEvent(event) ;
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      EPLcommonCDTO rescdto = null;
      EPLcommonCDTO reqcdto = new EPLcommonCDTO() ;

      try{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod02 START]");

        commonDTO = (EPlatonCommonDTO)event.getCommon();
        tpsvcinfo = event.getTPSVCINFODTO();
        rescdto = (EPLcommonCDTO)event.getRequest();

        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"신규계좌에 대한 정보를 셋팅한다");
        reqcdto.setAccountNumber("0001100100000048");
        reqcdto.setBankCode("03");
        reqcdto.setAccountNumber("9999999999999999");

        ////////////////////////////////////////////////////////////////////////////
        // TPSsendrecv TEST
        ////////////////////////////////////////////////////////////////////////////
        EPlatonCommonDTO tpmcommonDTO = event.getCommon() ;
        TPSVCINFODTO tpmtpsvcinfoDTO = event.getTPSVCINFODTO() ;
        response_event = TPSsendrecv.getInstance().CallEJB("spcashcard-callmethod02",event,reqcdto);
        response_event = TPSsendrecv.getInstance().CallEJB("spdeposit-callmethod01",event,reqcdto);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"3333333333333");
        ////////////////////////////////////////////////////////////////////////////

        switch( response_event.getTPSVCINFODTO().getErrorcode().charAt(0) )
        {
          case 'I' :
            response_event.setResponse(response_event.getResponse() );
            break;
          case 'E' :
            response_event.getTPSVCINFODTO().setErrorcode(response_event.getTPSVCINFODTO().getErrorcode() );
            String errorcode = "EFWK0047"+"|"+response_event.getTPSVCINFODTO().getErrorcode();
            tpsvcinfo.setErrorcode(errorcode);
            tpsvcinfo.setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
            break;
        }

        if( response_event.getResponse() == null )
        {
          LOGEJ.getInstance().printf(1,event,"response cdto null");
        }
        else{
          LOGEJ.getInstance().printf(1,event,"response cdto not null");
        }
      }
      catch(Exception ex){
        ex.printStackTrace();
        String errorcode = "EFWK0048"+"|"+tpsvcinfo.getErrorcode();
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
        LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
      }
      ////////////////////////////////////////////////////////////////////////////

      //상대시스템에서 보내준 CDTO을 관리하고 클라이언트로 넘긴다.
      event.setResponse(response_event.getResponse() );



      ////////////////////////////////////////////////////////////////////////////

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod02  END]"+event.getTPSVCINFODTO().getSystem_name()  );
      return event;
    }

    public EPlatonEvent callmethod03(EPlatonEvent event) throws CosesAppException
    {
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      EPLcommonCDTO reqcdto = new EPLcommonCDTO() ;

      reqcdto.setAccountNumber("0001100100000048");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("9999999999999999");

      event.setResponse(reqcdto);

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod03  START]"+event.getTPSVCINFODTO().getSystem_name()  );
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event," -->> call method 3이 호출되었습니다."  );
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod03  END]"+event.getTPSVCINFODTO().getSystem_name()  );

      return event;
    }


}



