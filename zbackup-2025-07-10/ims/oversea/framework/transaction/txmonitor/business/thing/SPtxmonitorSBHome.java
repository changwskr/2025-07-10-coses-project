package com.ims.oversea.framework.transaction.txmonitor.business.thing;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface SPtxmonitorSBHome extends javax.ejb.EJBHome {
  public SPtxmonitorSB create() throws CreateException, RemoteException;
}