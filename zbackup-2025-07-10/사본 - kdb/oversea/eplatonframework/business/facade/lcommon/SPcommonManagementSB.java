package com.kdb.oversea.eplatonframework.business.facade.lcommon;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.kdb.oversea.eplatonframework.transfer.*;

public interface SPcommonManagementSB extends javax.ejb.EJBObject {
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}