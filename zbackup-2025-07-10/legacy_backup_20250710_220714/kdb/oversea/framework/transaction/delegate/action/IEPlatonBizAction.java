package com.kdb.oversea.framework.transaction.delegate.action;

import com.chb.coses.framework.transfer.IEvent;

public abstract interface IEPlatonBizAction {

  // Methods
  void preAct(IEvent iEvent);
  IEvent act(IEvent iEvent) ;
  void postAct(IEvent iEvent);
}


