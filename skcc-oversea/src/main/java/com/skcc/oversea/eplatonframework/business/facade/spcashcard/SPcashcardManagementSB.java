package com.skcc.oversea.eplatonframework.business.facade.spcashcard;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.skcc.oversea.eplatonframework.transfer.*;

public interface SPcashcardManagementSB extends javax.ejb.EJBObject {
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}
