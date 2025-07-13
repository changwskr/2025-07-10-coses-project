package com.skcc.oversea.teller.business.facade;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.RemoteException;
import com.skcc.oversea.framework.exception.CosesAppException;
import com.skcc.oversea.eplatonframework.transfer.*;
import com.skcc.oversea.foundation.logej.*;

public interface ITellerManagementSB {

  public EPlatonEvent callmethod01(EPlatonEvent eplatonevent)
       throws CosesAppException, RemoteException;
  public EPlatonEvent callmethod02(EPlatonEvent eplatonevent)
       throws CosesAppException, RemoteException;

}




