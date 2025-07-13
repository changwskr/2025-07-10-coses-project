package com.skcc.oversea.eplatonframework.business.facade.spdeposit;

import javax.ejb.*;
import java.util.*;

public interface SPdepositManagementSBLocalHome extends javax.ejb.EJBLocalHome {
  public SPdepositManagementSBLocal create() throws CreateException;
}
