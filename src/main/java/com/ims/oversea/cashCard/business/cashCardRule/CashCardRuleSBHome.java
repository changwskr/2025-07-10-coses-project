package com.ims.oversea.cashCard.business.cashCardRule;

import javax.ejb.*;
import java.util.*;

public interface CashCardRuleSBHome extends javax.ejb.EJBLocalHome
{
    public CashCardRuleSB create() throws CreateException;
}