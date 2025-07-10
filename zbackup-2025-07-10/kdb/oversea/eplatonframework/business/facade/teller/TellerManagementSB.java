package com.kdb.oversea.eplatonframework.business.facade.teller;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

import com.kdb.oversea.eplatonframework.business.facade.ecommon.IECommonManagementSB;
import com.kdb.oversea.eplatonframework.transfer.*;

public interface TellerManagementSB extends javax.ejb.EJBObject, IECommonManagementSB
{
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}
