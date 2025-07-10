package com.ims.oversea.eplatonframework.business.thing.spcommo;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.eplatonframework.transfer.EPlatonCommonDTO;
import com.ims.oversea.framework.transaction.tcf.*;
import com.ims.oversea.eplatonframework.business.facade.cashCard.ICashCardManagementSB;
import com.ims.oversea.foundation.utility.*;
import com.ims.oversea.foundation.logej.*;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;
import com.ims.oversea.framework.transaction.constant.TCFConstants;
import com.ims.oversea.framework.transaction.bcf.BCF;
import com.chb.coses.framework.transfer.IDTO;

public interface SPcommoSB extends javax.ejb.EJBObject {
  public IDTO execute(String call_operation_class, String call_operation_method, IDTO transferDDTO, EPlatonEvent event) throws RemoteException;
}