package com.ims.oversea.deposit.business.facade;

import java.rmi.RemoteException;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.foundation.logej.*;


public interface IDepositManagementSB {
  /***************************************************************************
   * 테스트 샘플
   ***************************************************************************/
    public EPlatonEvent callmethod01(EPlatonEvent eplatonevent)
         throws CosesAppException, RemoteException;
    public EPlatonEvent callmethod02(EPlatonEvent eplatonevent)
         throws CosesAppException, RemoteException;
  /***************************************************************************/

}


