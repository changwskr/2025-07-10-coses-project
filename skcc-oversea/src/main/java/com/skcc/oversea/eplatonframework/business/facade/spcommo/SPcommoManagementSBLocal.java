package com.skcc.oversea.eplatonframework.business.facade.spcommo;

import javax.ejb.*;
import java.util.*;
import com.skcc.oversea.eplatonframework.transfer.*;

public interface SPcommoManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}
