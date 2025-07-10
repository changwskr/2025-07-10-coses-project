package com.ims.oversea.eplatonframework.business.facade.spcommo;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.ims.oversea.eplatonframework.transfer.*;

public interface SPcommoManagementSB extends javax.ejb.EJBObject {
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
  public EPlatonEvent notxexecute(EPlatonEvent event) throws RemoteException;
}