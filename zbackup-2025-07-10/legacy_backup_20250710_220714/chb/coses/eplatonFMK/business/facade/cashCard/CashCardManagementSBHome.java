﻿package com.chb.coses.eplatonFMK.business.facade.cashCard;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface CashCardManagementSBHome extends javax.ejb.EJBHome
{
  public CashCardManagementSB create() throws CreateException, RemoteException;
}