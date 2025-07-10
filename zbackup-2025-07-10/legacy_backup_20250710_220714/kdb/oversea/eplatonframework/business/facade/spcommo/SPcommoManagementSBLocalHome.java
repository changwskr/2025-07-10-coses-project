package com.kdb.oversea.eplatonframework.business.facade.spcommo;

import javax.ejb.*;
import java.util.*;

public interface SPcommoManagementSBLocalHome extends javax.ejb.EJBLocalHome {
  public SPcommoManagementSBLocal create() throws CreateException;
}