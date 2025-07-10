package com.kdb.oversea.deposit.business.facade;

import java.rmi.RemoteException;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.kdb.oversea.eplatonframework.transfer.*;
import com.kdb.oversea.foundation.logej.*;


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


