package com.skcc.oversea.framework.transaction.delegate.action;

import com.skcc.oversea.framework.transfer.IEvent;

public abstract interface IEPlatonBizAction {

  // Methods
  void preAct(IEvent iEvent);
  IEvent act(IEvent iEvent) ;
  void postAct(IEvent iEvent);
}




