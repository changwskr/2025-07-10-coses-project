package com.ims.oversea.framework.transaction.txmonitor.business.facade.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.ims.oversea.framework.transaction.txmonitor.business.thing.model.TransactionInfoDDTO;
import com.ims.oversea.framework.transaction.txmonitor.transfer.TransactionInfoCDTO;

public class DTOConverter {

  public static TransactionInfoCDTO getTransactionInfoCDTO(TransactionInfoCDTO transactionInfoCDTO,
      TransactionInfoDDTO transactionInfoDDTO)
  {

    transactionInfoCDTO.setBank_code(transactionInfoDDTO.getBank_code());
    transactionInfoCDTO.setTransaction_id(transactionInfoDDTO.getTransaction_id());
    transactionInfoCDTO.setHost_name(transactionInfoDDTO.getHost_name());
    transactionInfoCDTO.setSystem_name(transactionInfoDDTO.getSystem_name());
    transactionInfoCDTO.setMethod_name(transactionInfoDDTO.getMethod_name());
    transactionInfoCDTO.setBank_code(transactionInfoDDTO.getBank_code());
    transactionInfoCDTO.setBranch_code(transactionInfoDDTO.getBranch_code());
    transactionInfoCDTO.setUser_id(transactionInfoDDTO.getUser_id());
    transactionInfoCDTO.setChannel_type(transactionInfoDDTO.getChannel_type());
    transactionInfoCDTO.setBusiness_date(transactionInfoDDTO.getBusiness_date());
    transactionInfoCDTO.setRegister_date(transactionInfoDDTO.getRegister_date());
    transactionInfoCDTO.setEvent_no(transactionInfoDDTO.getEvent_no());
    transactionInfoCDTO.setTransaction_no(transactionInfoDDTO.getTransaction_no());
    transactionInfoCDTO.setOrg_seq(transactionInfoDDTO.getOrg_seq());
    transactionInfoCDTO.setIn_time(transactionInfoDDTO.getIn_time());
    transactionInfoCDTO.setOut_time(transactionInfoDDTO.getOut_time());
    transactionInfoCDTO.setResponse_time(transactionInfoDDTO.getResponse_time());
    transactionInfoCDTO.setError_code(transactionInfoDDTO.getError_code());
    transactionInfoCDTO.setIp_address(transactionInfoDDTO.getIp_address());
    transactionInfoCDTO.setTpfq(transactionInfoDDTO.getTpfq());
    transactionInfoCDTO.setStf_intime(transactionInfoDDTO.getStf_intime());
    transactionInfoCDTO.setStf_outtime(transactionInfoDDTO.getStf_outtime());
    transactionInfoCDTO.setStf_interval(transactionInfoDDTO.getStf_interval());
    transactionInfoCDTO.setBtf_intime(transactionInfoDDTO.getBtf_intime());
    transactionInfoCDTO.setBtf_outtime(transactionInfoDDTO.getBtf_outtime());
    transactionInfoCDTO.setBtf_interval(transactionInfoDDTO.getBtf_interval());
    transactionInfoCDTO.setEtf_intime(transactionInfoDDTO.getEtf_intime());
    transactionInfoCDTO.setEtf_outtime(transactionInfoDDTO.getEtf_outtime());
    transactionInfoCDTO.setEtf_interval(transactionInfoDDTO.getEtf_interval());
    return transactionInfoCDTO;
  }

  public static TransactionInfoDDTO getTransactionInfoDDTO(TransactionInfoCDTO transactionInfoCDTO)
  {
    TransactionInfoDDTO transactionInfoDDTO = new TransactionInfoDDTO();
    transactionInfoDDTO.setBank_code(transactionInfoCDTO.getBank_code());
    transactionInfoDDTO.setTransaction_id(transactionInfoCDTO.getTransaction_id());
    transactionInfoDDTO.setHost_name(transactionInfoCDTO.getHost_name());
    transactionInfoDDTO.setSystem_name(transactionInfoCDTO.getSystem_name());
    transactionInfoDDTO.setMethod_name(transactionInfoCDTO.getMethod_name());
    transactionInfoDDTO.setBank_code(transactionInfoCDTO.getBank_code());
    transactionInfoDDTO.setBranch_code(transactionInfoCDTO.getBranch_code());
    transactionInfoDDTO.setUser_id(transactionInfoCDTO.getUser_id());
    transactionInfoDDTO.setChannel_type(transactionInfoCDTO.getChannel_type());
    transactionInfoDDTO.setBusiness_date(transactionInfoCDTO.getBusiness_date());
    transactionInfoDDTO.setRegister_date(transactionInfoCDTO.getRegister_date());
    transactionInfoDDTO.setEvent_no(transactionInfoCDTO.getEvent_no());
    transactionInfoDDTO.setTransaction_no(transactionInfoCDTO.getTransaction_no());
    transactionInfoDDTO.setOrg_seq(transactionInfoCDTO.getOrg_seq());
    transactionInfoDDTO.setIn_time(transactionInfoCDTO.getIn_time());
    transactionInfoDDTO.setOut_time(transactionInfoCDTO.getOut_time());
    transactionInfoDDTO.setResponse_time(transactionInfoCDTO.getResponse_time());
    transactionInfoDDTO.setError_code(transactionInfoCDTO.getError_code());
    transactionInfoDDTO.setIp_address(transactionInfoCDTO.getIp_address());
    transactionInfoDDTO.setTpfq(transactionInfoCDTO.getTpfq());
    transactionInfoDDTO.setStf_intime(transactionInfoCDTO.getStf_intime());
    transactionInfoDDTO.setStf_outtime(transactionInfoCDTO.getStf_outtime());
    transactionInfoDDTO.setStf_interval(transactionInfoCDTO.getStf_interval());
    transactionInfoDDTO.setBtf_intime(transactionInfoCDTO.getBtf_intime());
    transactionInfoDDTO.setBtf_outtime(transactionInfoCDTO.getBtf_outtime());
    transactionInfoDDTO.setBtf_interval(transactionInfoCDTO.getBtf_interval());
    transactionInfoDDTO.setEtf_intime(transactionInfoCDTO.getEtf_intime());
    transactionInfoDDTO.setEtf_outtime(transactionInfoCDTO.getEtf_outtime());
    transactionInfoDDTO.setEtf_interval(transactionInfoCDTO.getEtf_interval());
    return transactionInfoDDTO;
  }
}