package com.ims.oversea.framework.transaction.txmonitor.business.rule;

import javax.ejb.*;
import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.transfer.ModifyDTO;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.model.TransactionInfoDDTO;
import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.SPtxmonitorSB;
import com.ims.oversea.framework.transaction.txmonitor.business.rule.helper.EJBUtilRule;


public class SPtxmonitorRuleSBBean extends com.chb.coses.framework.business.AbstractSessionBean implements ISPtxmonitorRuleSB
{
  public void ejbCreate() throws CreateException {
    /**@todo Complete this method*/
  }
  public TransactionInfoDDTO getTransactionInfoByTransactionNo(TransactionInfoDDTO transactionInfoDDTO,
      EPlatonEvent event) throws CosesAppException
  {
    SPtxmonitorSB remote = null;
    TransactionInfoDDTO ddto = null;
    try{
      remote = EJBUtilRule.getISPtxmonitorSB();
      ddto = remote.findTransactionInfoByHostSeq(transactionInfoDDTO.getTransaction_id());
    }
    catch(Exception ex){
      ex.printStackTrace();
      String errorcode = "EFWK0048"+"|"+event.getTPSVCINFODTO().getErrorcode();
      event.getTPSVCINFODTO().setErrorcode(errorcode);
      event.getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
    }
    ////////////////////////////////////////////////////////////////////////////

    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo  END]"+event.getTPSVCINFODTO().getSystem_name()  );
    return ddto;
  }

  public boolean setTransactionInfoByTransactionNo(TransactionInfoDDTO transactionInfoDDTO,
      EPlatonEvent event) throws CosesAppException
  {
    SPtxmonitorSB remote = null;
    TransactionInfoDDTO ddto = null;
    try{
      remote = EJBUtilRule.getISPtxmonitorSB();
      remote.updateTransactionInfoByHostseq(transactionInfoDDTO.getTransaction_id(),transactionInfoDDTO.getSystem_name());
    }
    catch(Exception ex){
      ex.printStackTrace();
      String errorcode = "EFWK0048"+"|"+event.getTPSVCINFODTO().getErrorcode();
      event.getTPSVCINFODTO().setErrorcode(errorcode);
      event.getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
      return false;
    }
    ////////////////////////////////////////////////////////////////////////////

    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo  END]"+event.getTPSVCINFODTO().getSystem_name()  );
    return true;
  }


  public boolean makeTransactionInfoByTransactionNo(TransactionInfoDDTO transactionInfoDDTO,
      EPlatonEvent event) throws CosesAppException
  {
    SPtxmonitorSB remote = null;
    TransactionInfoDDTO ddto = null;
    try{
      remote = EJBUtilRule.getISPtxmonitorSB();
      remote.insertTransactionInfo(event,transactionInfoDDTO);
    }
    catch(Exception ex){
      ex.printStackTrace();
      String errorcode = "EFWK0048"+"|"+event.getTPSVCINFODTO().getErrorcode();
      event.getTPSVCINFODTO().setErrorcode(errorcode);
      event.getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
      return false;
    }
    ////////////////////////////////////////////////////////////////////////////

    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo  END]"+event.getTPSVCINFODTO().getSystem_name()  );
    return true;
  }

  public TransactionInfoDDTO modifyTransactionInfo(TransactionInfoDDTO modifyDTO,
      EPlatonEvent event) throws CosesAppException
  {
    return null;
  }
}