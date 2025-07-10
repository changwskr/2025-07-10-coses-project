package com.ims.oversea.eplatonframework.business.facade.spdeposit;

import javax.ejb.*;
import java.util.*;
import com.ims.oversea.eplatonframework.transfer.*;

public interface SPdepositManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}