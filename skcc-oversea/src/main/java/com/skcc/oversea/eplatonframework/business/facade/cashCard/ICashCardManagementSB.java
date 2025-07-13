package com.skcc.oversea.eplatonframework.business.facade.cashCard;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.RemoteException;
import com.skcc.oversea.framework.exception.CosesAppException;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

public interface ICashCardManagementSB
{
  public EPlatonEvent execute(EPlatonEvent event) throws CosesAppException,RemoteException;
}




