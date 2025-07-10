package com.ims.oversea.framework.transaction.txmonitor.business.thing.entity.transactioninfo;

import javax.ejb.*;
import java.util.*;

public interface TransactionInfoEBHome extends javax.ejb.EJBLocalHome {
  public TransactionInfoEB create(String transaction_id, String host_name, String system_name, String method_name, String bank_code, String branch_code, String user_id, String channel_type, String business_date, String register_date, String event_no, String transaction_no, String org_seq, String in_time, String out_time, String response_time, String error_code, String ip_address, String tpfq, String stf_intime, String stf_outtime, String stf_interval, String btf_intime, String btf_outtime, String btf_interval, String etf_intime, String etf_outtime, String etf_interval) throws CreateException;
  public Collection findByTime(String intime, String outtime) throws FinderException;
  public TransactionInfoEB findByPrimaryKey(TransactionInfoEBPK pk) throws FinderException;
}