package com.kdb.oversea.framework.transaction.tcf;



import java.rmi.*;

import com.kdb.oversea.eplatonframework.transfer.EPlatonEvent;

public interface IBTF
{
  public EPlatonEvent execute(EPlatonEvent event) ;
  public abstract boolean BTF_SPinit() ;
  public abstract boolean BTF_SPmiddle() ;
  public abstract boolean BTF_SPend() ;

}