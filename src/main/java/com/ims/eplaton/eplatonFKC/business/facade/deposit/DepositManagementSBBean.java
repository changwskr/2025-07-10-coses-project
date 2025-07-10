package com.ims.eplaton.eplatonFKC.business.facade.deposit;

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;

import com.chb.coses.foundation.log.Log;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;

import com.ims.eplaton.eplatonFWK.transfer.EPlatonEvent;
import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.eplatonFWK.transfer.EPlatonCommonDTO;
import com.ims.eplaton.framework.tcf.*;
import com.ims.eplaton.eplatonFWK.business.facade.deposit.IDepositManagementSB;
import com.ims.eplaton.foundation.helper.*;
import com.ims.eplaton.foundation.helper.logej.*;


/**
 * 실제 업무 시스템의 facade단이 정의되는 부분이다.
 * 현재 이 각 업무시스템의 facade단을 직접가지고 감으로서 모든 시스템을 하나로 로직을 관리
 * 하고 트랜잭션을 일률적으로 관리하기 위한 로직이다.
 * 실제 호출되는 세션빈에 대한 메소드는 execute()이다.
 * 이 메소드내에서 각 업무시스템과의 연계를 위한 하나의 메소드가 정의 된다.
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DepositManagementSBBean extends com.chb.coses.framework.business.AbstractSessionBean
    implements IDepositManagementSB, SessionBean
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
            tpsvcinfo.setErrorcode("EFWK0041");
            tpsvcinfo.setError_message(this.getClass().getName()+ ".execute():" + re.toString());
            break;
          case 'E' :
            String errorcode = "EFWK0041"+"|"+tpsvcinfo.getErrorcode();
            tpsvcinfo.setErrorcode(errorcode);
            tpsvcinfo.setError_message(this.getClass().getName()+ ".execute():" + re.toString());
            break;
        }
      }
      //////////////////////////////////////////////////////////////////////////
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,re);
      LOGEJ.getInstance().printf(1,resp_event,this.getClass().getName()+ ".execute():" + re.toString());

    }

    return resp_event;

  }



}
