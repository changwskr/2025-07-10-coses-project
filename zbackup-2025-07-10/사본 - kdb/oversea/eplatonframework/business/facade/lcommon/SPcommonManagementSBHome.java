﻿package com.kdb.oversea.eplatonframework.business.facade.lcommon;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

import com.kdb.oversea.eplatonframework.transfer.*;

public interface SPcommonManagementSBHome extends javax.ejb.EJBHome {
  public SPcommonManagementSB create() throws CreateException, RemoteException;
}