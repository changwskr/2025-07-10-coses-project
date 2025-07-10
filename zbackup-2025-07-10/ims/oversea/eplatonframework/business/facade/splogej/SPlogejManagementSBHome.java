package com.ims.oversea.eplatonframework.business.facade.splogej;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface SPlogejManagementSBHome extends javax.ejb.EJBHome {
  public SPlogejManagementSB create() throws CreateException, RemoteException;
}