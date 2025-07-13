package com.skcc.oversea.eplatonframework.business;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface EPlatonBizDelegateSBHome extends EJBHome
{
    public com.kdb.oversea.eplatonframework.business.EPlatonBizDelegateSB create() throws CreateException, RemoteException;
}


