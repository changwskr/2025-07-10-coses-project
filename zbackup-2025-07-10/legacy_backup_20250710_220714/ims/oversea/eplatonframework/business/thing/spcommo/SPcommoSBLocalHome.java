package com.ims.oversea.eplatonframework.business.thing.spcommo;

import javax.ejb.*;
import java.util.*;

public interface SPcommoSBLocalHome extends javax.ejb.EJBLocalHome {
  public SPcommoSBLocal create() throws CreateException;
}