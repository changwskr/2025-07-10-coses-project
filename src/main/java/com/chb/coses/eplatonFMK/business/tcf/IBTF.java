package com.chb.coses.eplatonFMK.business.tcf;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.*;

import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

public interface IBTF
{
  public EPlatonEvent execute(EPlatonEvent event) ;
  public abstract boolean BTF_SPinit() ;
  public abstract boolean BTF_SPmiddle() ;
  public abstract boolean BTF_SPend() ;

}