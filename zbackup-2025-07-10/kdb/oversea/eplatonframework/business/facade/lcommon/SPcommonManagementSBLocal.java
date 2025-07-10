package com.kdb.oversea.eplatonframework.business.facade.lcommon;

import javax.ejb.*;
import java.util.*;
import com.kdb.oversea.eplatonframework.transfer.*;

public interface SPcommonManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}