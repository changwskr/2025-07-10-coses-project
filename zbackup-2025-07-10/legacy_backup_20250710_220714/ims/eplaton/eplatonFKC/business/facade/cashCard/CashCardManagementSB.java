package com.ims.eplaton.eplatonFKC.business.facade.cashCard;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

import com.ims.eplaton.eplatonFWK.business.facade.cashCard.ICashCardManagementSB;
import com.ims.eplaton.eplatonFWK.transfer.*;

public interface CashCardManagementSB extends javax.ejb.EJBObject, ICashCardManagementSB
{
  public EPlatonEvent execute(EPlatonEvent event) throws RemoteException;
}



