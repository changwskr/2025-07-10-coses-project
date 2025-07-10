package com.ims.oversea.common.business.facade;

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;
import java.math.BigDecimal;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.deposit.business.facade.*;
import com.chb.coses.deposit.transfer.*;
import com.ims.oversea.framework.transaction.helper.Convert;
import com.ims.oversea.foundation.logej.*;
import com.ims.oversea.framework.transaction.tpmutil.TPMSutil;
import com.ims.oversea.framework.transaction.tpmutil.TPSsendrecv;
import com.ims.oversea.eplatonframework.transfer.*;
import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.ims.oversea.eplatonframework.transfer.EPLcommonCDTO;
import com.ims.oversea.framework.transaction.bean.AbstractBIZTCF;
import com.ims.oversea.common.business.facade.helper.EJBUtilFacade;
import com.ims.oversea.eplatonframework.business.rule.spcommoRule.*;
import com.ims.oversea.common.business.facade.helper.JNDINamesFacade;
import com.ims.oversea.common.business.thing.model.EPLcommonDDTO;
import com.chb.coses.framework.transfer.IDTO;

public class CommonManagementSBean extends AbstractBIZTCF implements ICommonManagementSB{

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
        printf(1,"==================[callmethod01 START]");

        commonDTO = (EPlatonCommonDTO)event.getCommon();
        tpsvcinfo = event.getTPSVCINFODTO();
        rescdto = (EPLcommonCDTO)event.getRequest();

        printf(1,"신규계좌에 대한 정보를 셋팅한다");
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
        printf(1,"3333333333333");
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

      printf(1,"==================[callmethod01  END]"+event.getTPSVCINFODTO().getSystem_name()  );
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
        printf(1,"==================[callmethod02 START]");

        commonDTO = (EPlatonCommonDTO)event.getCommon();
        tpsvcinfo = event.getTPSVCINFODTO();
        rescdto = (EPLcommonCDTO)event.getRequest();

        printf(1,"신규계좌에 대한 정보를 셋팅한다");
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
        printf(1,"3333333333333");
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

      printf(1,"==================[callmethod02  END]"+event.getTPSVCINFODTO().getSystem_name()  );
      return event;
    }

    public EPlatonEvent callmethod03() throws CosesAppException
    {
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      EPLcommonCDTO reqcdto = new EPLcommonCDTO() ;

      try{

        reqcdto.setAccountNumber("0001100100000048");
        reqcdto.setBankCode("03");
        reqcdto.setAccountNumber("9999999999999999");

        event.setResponse(reqcdto);

        printf(1,"==================[callmethod03  START]"+event.getTPSVCINFODTO().getSystem_name()  );
        printf(1," -->> call method 3이 호출되었습니다."  );


        ////////////////////////////////////////////////////////////////////////////////////
        //1.dtoconverter
        EPLcommonDDTO ddto = new EPLcommonDDTO();

        //2.rule
        SPcommoRuleSB  remote = EJBUtilFacade.getISPcommoRuleSB();
        EPLmessageParameter parameter = event.getMessageParameter();
        ddto = (EPLcommonDDTO)remote.execute(
                         JNDINamesFacade.COMMO_RULE_SB_BEAN.getName(),
                         JNDINamesFacade.METHOD_GETHOSTCARD,
                         (IDTO)ddto,
                         event);

        ////////////////////////////////////////////////////////////////////////////////////

        printf(1,"==================[callmethod03  END]"+event.getTPSVCINFODTO().getSystem_name()  );
      }
      catch(RemoteException ex){
        ex.printStackTrace();
      }

      return event;
    }


}

