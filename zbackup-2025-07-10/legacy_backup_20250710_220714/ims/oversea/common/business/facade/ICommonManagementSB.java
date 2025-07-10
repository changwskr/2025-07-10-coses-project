package com.ims.oversea.common.business.facade;

import java.rmi.RemoteException;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.ims.oversea.eplatonframework.transfer.*;
import com.chb.coses.framework.transfer.IDTO;
import com.ims.oversea.foundation.logej.*;


public interface ICommonManagementSB {
  /***************************************************************************
   * 테스트 샘플
   ***************************************************************************/
    public EPlatonEvent callmethod01(EPlatonEvent eplatonevent)
         throws CosesAppException, RemoteException;
    public EPlatonEvent callmethod02(EPlatonEvent eplatonevent)
         throws CosesAppException, RemoteException;
    public EPlatonEvent callmethod03()
         throws CosesAppException, RemoteException;

  /***************************************************************************/

}

