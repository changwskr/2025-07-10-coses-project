package com.ims.oversea.framework.transaction.txmonitor.business.facade;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.RemoteException;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.foundation.logej.*;


public interface ITXmonitorManagementSB {
  /***************************************************************************
   * 테스트 샘플
   ***************************************************************************/
    public EPlatonEvent getTransactionInfoByTransactionNo(EPlatonEvent eplatonevent)
         throws CosesAppException, RemoteException;
    public EPlatonEvent setTransactionInfoByTransactionNo(EPlatonEvent eplatonevent)
         throws CosesAppException, RemoteException;
    public EPlatonEvent makeTransactionInfoByTransactionNo(EPlatonEvent eplatonevent)
         throws CosesAppException, RemoteException;
  /***************************************************************************/

}


