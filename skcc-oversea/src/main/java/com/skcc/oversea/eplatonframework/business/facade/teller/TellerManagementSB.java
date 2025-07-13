package com.skcc.oversea.eplatonframework.business.facade.teller;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

import com.skcc.oversea.eplatonframework.business.facade.ecommon.IECommonManagementSB;
import com.skcc.oversea.eplatonframework.transfer.*;

public interface TellerManagementSB extends javax.ejb.EJBObject, IECommonManagementSB
{
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}

