﻿package com.kdb.oversea.eplatonframework.business.facade.deposit;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface DepositManagementSBHome extends javax.ejb.EJBHome
{
  public DepositManagementSB create() throws CreateException, RemoteException;
}