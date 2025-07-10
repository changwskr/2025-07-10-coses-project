package com.kdb.oversea.eplatonframework.business.facade.spcommo;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.kdb.oversea.eplatonframework.transfer.*;

public interface SPcommoManagementSB extends javax.ejb.EJBObject {
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}