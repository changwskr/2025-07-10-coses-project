package com.ims.oversea.eplatonframework.business.facade.splogej;

import javax.ejb.*;
import java.util.*;

public interface SPlogejManagementSBLocalHome extends javax.ejb.EJBLocalHome {
  public SPlogejManagementSBLocal create() throws CreateException;
}