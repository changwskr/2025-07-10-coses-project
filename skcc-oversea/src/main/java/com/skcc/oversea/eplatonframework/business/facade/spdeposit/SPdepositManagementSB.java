package com.skcc.oversea.eplatonframework.business.facade.spdeposit;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.skcc.oversea.eplatonframework.transfer.*;

public interface SPdepositManagementSB extends javax.ejb.EJBObject {
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}
