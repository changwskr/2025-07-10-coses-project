package com.ims.eplaton.eplatonFWK.business.operation;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.IEvent;
import com.ims.eplaton.eplatonFWK.transfer.EPlatonEvent;
// 이로직은 framework으로 상향조정한다.

public interface IBizOperation
{
  EPlatonEvent act(EPlatonEvent iEvent) throws BizActionException;
}

