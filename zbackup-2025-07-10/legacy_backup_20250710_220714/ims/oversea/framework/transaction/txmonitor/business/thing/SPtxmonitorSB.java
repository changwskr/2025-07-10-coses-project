package com.ims.oversea.framework.transaction.txmonitor.business.thing;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.model.*;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;
import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;


public interface SPtxmonitorSB extends javax.ejb.EJBObject {
  public boolean updateTransactionInfoByHostseq(String hostseq,String hostname) throws CosesAppException, RemoteException;
  public boolean insertTransactionInfo(EPlatonEvent event,TransactionInfoDDTO transactionDDTO) throws CosesAppException,RemoteException;
  public TransactionInfoDDTO findTransactionInfoByHostSeq(String hostseq) throws CosesAppException, RemoteException;
  public TransactionInfoListDDTO findTransactionInfoByTime(String intime, String outtime) throws CosesAppException, RemoteException;
}