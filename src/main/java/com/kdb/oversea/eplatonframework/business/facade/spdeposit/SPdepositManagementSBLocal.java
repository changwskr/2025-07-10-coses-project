package com.kdb.oversea.eplatonframework.business.facade.spdeposit;

import javax.ejb.*;
import java.util.*;
import com.kdb.oversea.eplatonframework.transfer.*;

public interface SPdepositManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}