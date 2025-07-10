package com.ims.oversea.eplatonframework.business.rule.spcommoRule;

import javax.ejb.*;
import java.util.*;

public interface SPcommoRuleSBLocalHome extends javax.ejb.EJBLocalHome {
  public SPcommoRuleSBLocal create() throws CreateException;
}