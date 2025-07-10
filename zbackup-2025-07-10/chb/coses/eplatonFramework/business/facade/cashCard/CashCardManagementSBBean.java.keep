package com.chb.coses.eplatonFramework.business.facade.cashCard;

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;

import com.chb.coses.foundation.log.Log;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;

import com.chb.coses.eplatonFramework.transfer.EPlatonEvent;
import com.chb.coses.eplatonFramework.transfer.EPlatonCommonDTO;
import com.chb.coses.eplatonFramework.business.tcf.TCF;
import com.chb.coses.eplatonFramework.business.tcf.TCF_test;
import com.chb.coses.eplatonFramework.business.facade.cashCard.ICashCardManagementSB;


public class CashCardManagementSBBean extends com.chb.coses.framework.business.AbstractSessionBean
    implements ICashCardManagementSB
{
  public void ejbCreate() throws CreateException {
    /**@todo Complete this method*/
  }
  public EPlatonEvent execute(EPlatonEvent event) throws CosesAppException
  {

    try
    {
      //////////////////////////////////////////////////////////////////////////
      // 트랜잭션 정보를 관리한다.
      //////////////////////////////////////////////////////////////////////////
      System.out.println("=========================================TCF ST");
      TCF tcf = new TCF();
      tcf.execute(event);
      System.out.println("=========================================TCF END");

    }
    /*
    catch (RemoteException re)
    {
      throw new EJBException(re);
    }
    */
    catch (Exception re)
    {
      throw new EJBException(re);
    }

    return event;
  }

}
