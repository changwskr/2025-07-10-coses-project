package com.ims.oversea.framework.transaction.tcf;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.*;
import javax.ejb.*;

import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;

public interface ITCF
{
  public EPlatonEvent execute(EPlatonEvent pevent,SessionContext session_context,String transaction_mode) ;

}