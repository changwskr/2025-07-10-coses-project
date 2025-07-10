package com.ims.oversea.eplatonframework.business.facade.lcommon;

import javax.ejb.*;
import java.util.*;
import com.ims.oversea.eplatonframework.transfer.*;

public interface SPcommonManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}