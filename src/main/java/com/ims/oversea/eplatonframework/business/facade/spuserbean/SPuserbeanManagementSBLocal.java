package com.ims.oversea.eplatonframework.business.facade.spuserbean;

import javax.ejb.*;
import java.util.*;
import com.ims.oversea.eplatonframework.transfer.*;

public interface SPuserbeanManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}