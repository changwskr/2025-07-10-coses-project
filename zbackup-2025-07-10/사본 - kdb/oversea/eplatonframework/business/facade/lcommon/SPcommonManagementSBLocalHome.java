package com.kdb.oversea.eplatonframework.business.facade.lcommon;

import javax.ejb.*;
import java.util.*;

public interface SPcommonManagementSBLocalHome extends javax.ejb.EJBLocalHome {
  public SPcommonManagementSBLocal create() throws CreateException;
}