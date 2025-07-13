package com.skcc.oversea.eplatonframework.business.facade.lcommon;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.skcc.oversea.eplatonframework.transfer.*;

public interface SPcommonManagementSB extends javax.ejb.EJBObject {
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}
