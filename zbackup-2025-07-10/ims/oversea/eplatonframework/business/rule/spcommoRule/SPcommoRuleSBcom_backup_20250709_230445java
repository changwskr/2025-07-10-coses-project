package com.ims.oversea.eplatonframework.business.rule.spcommoRule;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;
import com.chb.coses.framework.transfer.IDTO;

public interface SPcommoRuleSB extends javax.ejb.EJBObject {
  public IDTO execute(String call_operation_class,
                                  String call_operation_method,
                                  IDTO transferDDTO,
                                  EPlatonEvent event) throws RemoteException;
}