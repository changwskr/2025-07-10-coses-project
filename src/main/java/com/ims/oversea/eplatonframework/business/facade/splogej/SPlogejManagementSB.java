package com.ims.oversea.eplatonframework.business.facade.splogej;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.ims.oversea.eplatonframework.transfer.*;

public interface SPlogejManagementSB extends javax.ejb.EJBObject {
  public EPlatonEvent execute(EPlatonEvent event,char type) throws RemoteException;
}