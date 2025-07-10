package com.ims.oversea.framework.transaction.txmonitor.business.rule;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.rmi.*;

import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.transfer.ModifyDTO;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.model.TransactionInfoDDTO;
import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;

public interface ISPtxmonitorRuleSB {
  public TransactionInfoDDTO getTransactionInfoByTransactionNo(TransactionInfoDDTO transactionInfoDDTO,
          EPlatonEvent event) throws CosesAppException, RemoteException;

  public boolean setTransactionInfoByTransactionNo(TransactionInfoDDTO transactionInfoDDTO,
          EPlatonEvent event) throws CosesAppException, RemoteException;

  public boolean makeTransactionInfoByTransactionNo(TransactionInfoDDTO transactionInfoDDTO,
          EPlatonEvent event) throws CosesAppException, RemoteException;

  public TransactionInfoDDTO modifyTransactionInfo(TransactionInfoDDTO modifyDTO,
          EPlatonEvent event) throws CosesAppException, RemoteException;

}

