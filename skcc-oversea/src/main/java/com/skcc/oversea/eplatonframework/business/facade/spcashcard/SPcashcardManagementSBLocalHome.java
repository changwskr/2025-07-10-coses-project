package com.skcc.oversea.eplatonframework.business.facade.spcashcard;

import javax.ejb.*;
import java.util.*;

public interface SPcashcardManagementSBLocalHome extends javax.ejb.EJBLocalHome {
  public SPcashcardManagementSBLocal create() throws CreateException;
}
