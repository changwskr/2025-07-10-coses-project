package com.skcc.oversea.eplatonframework.business.facade.lcommon;

import javax.ejb.*;
import java.util.*;
import com.skcc.oversea.eplatonframework.transfer.*;

public interface SPcommonManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}
