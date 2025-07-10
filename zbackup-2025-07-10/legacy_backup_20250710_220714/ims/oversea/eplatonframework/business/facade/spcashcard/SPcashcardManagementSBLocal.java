package com.ims.oversea.eplatonframework.business.facade.spcashcard;

import javax.ejb.*;
import java.util.*;
import com.ims.oversea.eplatonframework.transfer.*;

public interface SPcashcardManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}