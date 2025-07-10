package com.ims.eplaton.eplatonFKC.business.facade.deposit;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

import com.ims.eplaton.eplatonFWK.business.facade.deposit.IDepositManagementSB;
import com.ims.eplaton.eplatonFWK.transfer.*;

public interface DepositManagementSB extends javax.ejb.EJBObject, IDepositManagementSB
{
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}