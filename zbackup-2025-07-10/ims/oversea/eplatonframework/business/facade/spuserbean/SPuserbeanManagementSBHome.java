package com.ims.oversea.eplatonframework.business.facade.spuserbean;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface SPuserbeanManagementSBHome extends javax.ejb.EJBHome {
  public SPuserbeanManagementSB create() throws CreateException, RemoteException;
}