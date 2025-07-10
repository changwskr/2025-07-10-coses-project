package com.ims.eplaton.eplatonFWK.business.facade.deposit;

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
import com.ims.eplaton.eplatonFWK.transfer.EPlatonEvent;

public interface IDepositManagementSB
{
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}


