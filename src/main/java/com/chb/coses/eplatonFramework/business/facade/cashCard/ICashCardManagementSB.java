package com.chb.coses.eplatonFramework.business.facade.cashCard;

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
import com.chb.coses.eplatonFramework.transfer.EPlatonEvent;

public interface ICashCardManagementSB
{
  public EPlatonEvent execute(EPlatonEvent event) throws CosesAppException, RemoteException;
}


