package com.kdb.oversea.eplatonframework.business.facade.ecommon;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.rmi.RemoteException;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.kdb.oversea.eplatonframework.transfer.EPlatonEvent;

public interface IECommonManagementSB
{
  public EPlatonEvent execute(EPlatonEvent event) throws CosesAppException, RemoteException;
}