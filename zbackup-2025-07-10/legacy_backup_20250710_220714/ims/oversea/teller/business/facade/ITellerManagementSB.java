package com.ims.oversea.teller.business.facade;

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

public interface ITellerManagementSB {

  public EPlatonEvent callmethod01(EPlatonEvent eplatonevent)
       throws CosesAppException, RemoteException;
  public EPlatonEvent callmethod02(EPlatonEvent eplatonevent)
       throws CosesAppException, RemoteException;

}


