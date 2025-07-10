package com.kdb.oversea.eplatonframework.business.delegate.action;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.chb.coses.framework.transfer.IEvent;
// 이로직은 framework으로 상향조정한다.

public abstract interface IEPlatonBizAction {

  // Methods
  void preAct(IEvent iEvent);
  IEvent act(IEvent iEvent) ;
  void postAct(IEvent iEvent);
}

