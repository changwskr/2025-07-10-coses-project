package com.skcc.oversea.eplatonframework.business.facade.spdeposit;

import javax.ejb.*;
import java.util.*;
import com.skcc.oversea.eplatonframework.transfer.*;

public interface SPdepositManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}
