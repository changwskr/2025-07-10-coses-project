package com.ims.oversea.eplatonframework.business.facade.splogej;

import javax.ejb.*;
import java.util.*;
import com.ims.oversea.eplatonframework.transfer.*;

public interface SPlogejManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event,char type);
}