package com.chb.coses.eplatonFramework.business.facade.ecommon;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface ECommonManagementSBHome extends javax.ejb.EJBHome
{
  public ECommonManagementSB create() throws CreateException, RemoteException;
}