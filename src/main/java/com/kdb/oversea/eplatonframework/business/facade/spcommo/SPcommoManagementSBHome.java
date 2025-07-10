package com.kdb.oversea.eplatonframework.business.facade.spcommo;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface SPcommoManagementSBHome extends javax.ejb.EJBHome {
  public SPcommoManagementSB create() throws CreateException, RemoteException;
}