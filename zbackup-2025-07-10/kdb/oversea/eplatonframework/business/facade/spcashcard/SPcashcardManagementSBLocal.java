﻿package com.kdb.oversea.eplatonframework.business.facade.spcashcard;

import javax.ejb.*;
import java.util.*;
import com.kdb.oversea.eplatonframework.transfer.*;

public interface SPcashcardManagementSBLocal extends javax.ejb.EJBLocalObject {
  public EPlatonEvent execute(EPlatonEvent event);
}