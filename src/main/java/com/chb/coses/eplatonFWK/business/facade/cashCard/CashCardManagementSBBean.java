package com.chb.coses.eplatonFWK.business.facade.cashCard;

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;

import com.chb.coses.foundation.log.Log;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;

import com.chb.coses.eplatonFWK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFWK.transfer.*;
import com.chb.coses.eplatonFWK.transfer.EPlatonCommonDTO;
import com.chb.coses.eplatonFWK.business.tcf.TCF;
import com.chb.coses.eplatonFWK.business.tcf.TCF_test;
import com.chb.coses.eplatonFWK.business.facade.cashCard.ICashCardManagementSB;
import com.chb.coses.eplatonFWK.business.helper.logej.LOGEJ;

public class CashCardManagementSBBean extends com.chb.coses.framework.business.AbstractSessionBean
    implements ICashCardManagementSB
{
  public void ejbCreate() throws CreateException {
    /**@todo Complete this method*/
  }

  public EPlatonEvent execute(EPlatonEvent event)
  {
    EPlatonEvent resp_event = null;
    try
    {
      resp_event = event;

      //////////////////////////////////////////////////////////////////////////
      // 트랜잭션 정보를 관리한다.
      //////////////////////////////////////////////////////////////////////////
      TCF tcf = new TCF();
      resp_event = tcf.execute(event);

    }
    catch (Exception re)
    {

      re.printStackTrace();
      //////////////////////////////////////////////////////////////////////////
      // 에러코드 관리
      //////////////////////////////////////////////////////////////////////////
      {
        TPSVCINFODTO tpsvcinfo = ((EPlatonEvent)resp_event).getTPSVCINFODTO();

        switch( tpsvcinfo.getErrorcode().charAt(0) )
        {
          case 'I' :
            tpsvcinfo.setErrorcode("EFWK0037");
            tpsvcinfo.setError_message(this.getClass().getName()+ ".execute():" + re.toString());
            break;
          case 'E' :
            String errorcode = "EFWK0037"+"|"+tpsvcinfo.getErrorcode();
            tpsvcinfo.setErrorcode(errorcode);
            tpsvcinfo.setError_message(this.getClass().getName()+ ".execute():" + re.toString());
            break;
        }
      }
      //////////////////////////////////////////////////////////////////////////
      LOGEJ.getInstance().eprintf((EPlatonEvent)event,re);
      LOGEJ.getInstance().printf(resp_event,this.getClass().getName()+ ".execute():" + re.toString());

    }

    return resp_event;

  }



}
