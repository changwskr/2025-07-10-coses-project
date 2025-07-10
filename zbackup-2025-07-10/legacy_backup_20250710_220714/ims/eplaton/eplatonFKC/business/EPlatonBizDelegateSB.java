package com.ims.eplaton.eplatonFKC.business;



import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.chb.coses.framework.business.delegate.*;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.framework.exception.*;

public interface EPlatonBizDelegateSB extends EJBObject,IBizDelegate
{
  public IEvent execute(IEvent event) throws BizDelegateException, RemoteException;
}
