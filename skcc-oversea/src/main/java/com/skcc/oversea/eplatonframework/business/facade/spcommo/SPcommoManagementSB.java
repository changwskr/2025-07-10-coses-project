package com.skcc.oversea.eplatonframework.business.facade.spcommo;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.skcc.oversea.eplatonframework.transfer.*;

public interface SPcommoManagementSB extends javax.ejb.EJBObject {
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}
