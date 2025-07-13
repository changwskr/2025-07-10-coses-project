package com.skcc.oversea.eplatonframework.business.facade.spdeposit;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface SPdepositManagementSBHome extends javax.ejb.EJBHome {
  public SPdepositManagementSB create() throws CreateException, RemoteException;
}
