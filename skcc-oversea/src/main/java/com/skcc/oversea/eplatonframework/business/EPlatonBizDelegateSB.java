package com.skcc.oversea.eplatonframework.business;



import javax.ejb.*;
import java.util.*;
import java.rmi.*;

import com.skcc.oversea.framework.business.delegate.*;
import com.skcc.oversea.framework.transfer.*;
import com.skcc.oversea.framework.exception.*;

public interface EPlatonBizDelegateSB extends EJBObject,IBizDelegate
{
  public IEvent execute(IEvent event) throws BizDelegateException, RemoteException;
}



