package com.skcc.oversea.eplatonframework.business;

import com.skcc.oversea.framework.business.delegate.IBizDelegate;
import com.skcc.oversea.framework.transfer.IEvent;
import com.skcc.oversea.framework.exception.BizDelegateException;

/**
 * EPlaton Business Delegate Service Interface
 * Spring Boot service interface replacing EJB remote interface
 */
public interface EPlatonBizDelegateSB extends IBizDelegate {

  /**
   * Execute business logic
   * 
   * @param event The business event
   * @return Processed event
   * @throws BizDelegateException if business logic fails
   */
  IEvent execute(IEvent event) throws BizDelegateException;
}
