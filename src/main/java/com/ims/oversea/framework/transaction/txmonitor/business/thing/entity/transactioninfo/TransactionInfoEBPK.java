package com.ims.oversea.framework.transaction.txmonitor.business.thing.entity.transactioninfo;

import java.io.*;

public class TransactionInfoEBPK implements Serializable {




















































  public String transaction_id;

  public TransactionInfoEBPK() {
  }

  public TransactionInfoEBPK(String transaction_id) {
    this.transaction_id = transaction_id;
  }
  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        TransactionInfoEBPK that = (TransactionInfoEBPK) obj;
        return (((this.transaction_id == null) && (that.transaction_id == null)) || (this.transaction_id != null && this.transaction_id.equals(that.transaction_id)));
      }
    }
    return false;
  }
  public int hashCode() {
    return transaction_id.hashCode();
  }
}