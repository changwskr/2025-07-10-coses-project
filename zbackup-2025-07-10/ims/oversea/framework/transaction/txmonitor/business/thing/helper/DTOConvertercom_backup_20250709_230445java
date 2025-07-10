package com.ims.oversea.framework.transaction.txmonitor.business.thing.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.ims.oversea.framework.transaction.txmonitor.business.thing.model.TransactionInfoDDTO;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.entity.transactioninfo.*;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;
import com.chb.coses.foundation.jndi.*;

public class DTOConverter {

  public static TransactionInfoDDTO getTransactionInfoDDTO(TransactionInfoEB en )
  {
    TransactionInfoDDTO transactionDDTO = new TransactionInfoDDTO();
    transactionDDTO.setBank_code(en.getBank_code());
    transactionDDTO.setTransaction_id(en.getTransaction_id());
    transactionDDTO.setHost_name(en.getHost_name());
    transactionDDTO.setSystem_name(en.getSystem_name());
    transactionDDTO.setMethod_name(en.getMethod_name());
    transactionDDTO.setBank_code(en.getBank_code());
    transactionDDTO.setBranch_code(en.getBranch_code());
    transactionDDTO.setUser_id(en.getUser_id());
    transactionDDTO.setChannel_type(en.getChannel_type());
    transactionDDTO.setBusiness_date(en.getBusiness_date());
    transactionDDTO.setRegister_date(en.getRegister_date());
    transactionDDTO.setEvent_no(en.getEvent_no());
    transactionDDTO.setTransaction_no(en.getTransaction_no());
    transactionDDTO.setOrg_seq(en.getOrg_seq());
    transactionDDTO.setIn_time(en.getIn_time());
    transactionDDTO.setOut_time(en.getOut_time());
    transactionDDTO.setResponse_time(en.getResponse_time());
    transactionDDTO.setError_code(en.getError_code());
    transactionDDTO.setIp_address(en.getIp_address());
    transactionDDTO.setTpfq(en.getTpfq());
    transactionDDTO.setStf_intime(en.getStf_intime());
    transactionDDTO.setStf_outtime(en.getStf_outtime());
    transactionDDTO.setStf_interval(en.getStf_interval());
    transactionDDTO.setBtf_intime(en.getBtf_intime());
    transactionDDTO.setBtf_outtime(en.getBtf_outtime());
    transactionDDTO.setBtf_interval(en.getBtf_interval());
    transactionDDTO.setEtf_intime(en.getEtf_intime());
    transactionDDTO.setEtf_outtime(en.getEtf_outtime());
    transactionDDTO.setEtf_interval(en.getEtf_interval());

    return transactionDDTO;
  }

  public static void setTransactionInfoEB(TransactionInfoDDTO transactionInfoDDTO, TransactionInfoEB transactionInfoEB)
  {
    transactionInfoEB.setBank_code(transactionInfoDDTO.getBank_code());
    transactionInfoEB.setTransaction_id(transactionInfoDDTO.getTransaction_id());
    transactionInfoEB.setHost_name(transactionInfoDDTO.getHost_name());
    transactionInfoEB.setSystem_name(transactionInfoDDTO.getSystem_name());
    transactionInfoEB.setMethod_name(transactionInfoDDTO.getMethod_name());
    transactionInfoEB.setBank_code(transactionInfoDDTO.getBank_code());
    transactionInfoEB.setBranch_code(transactionInfoDDTO.getBranch_code());
    transactionInfoEB.setUser_id(transactionInfoDDTO.getUser_id());
    transactionInfoEB.setChannel_type(transactionInfoDDTO.getChannel_type());
    transactionInfoEB.setBusiness_date(transactionInfoDDTO.getBusiness_date());
    transactionInfoEB.setRegister_date(transactionInfoDDTO.getRegister_date());
    transactionInfoEB.setTransaction_no(transactionInfoDDTO.getTransaction_no());
    transactionInfoEB.setOrg_seq(transactionInfoDDTO.getOrg_seq());
    transactionInfoEB.setIn_time(transactionInfoDDTO.getIn_time());
    transactionInfoEB.setOut_time(transactionInfoDDTO.getOut_time());
    transactionInfoEB.setResponse_time(transactionInfoDDTO.getResponse_time());
    transactionInfoEB.setError_code(transactionInfoDDTO.getError_code());
    transactionInfoEB.setIp_address(transactionInfoDDTO.getIp_address());
    transactionInfoEB.setTpfq(transactionInfoDDTO.getTpfq());
    transactionInfoEB.setStf_intime(transactionInfoDDTO.getStf_intime());
    transactionInfoEB.setStf_outtime(transactionInfoDDTO.getStf_outtime());
    transactionInfoEB.setStf_interval(transactionInfoDDTO.getStf_interval());
    transactionInfoEB.setBtf_intime(transactionInfoDDTO.getBtf_intime());
    transactionInfoEB.setBtf_outtime(transactionInfoDDTO.getBtf_outtime());
    transactionInfoEB.setBtf_interval(transactionInfoDDTO.getBtf_interval());
    transactionInfoEB.setEtf_intime(transactionInfoDDTO.getEtf_intime());
    transactionInfoEB.setEtf_outtime(transactionInfoDDTO.getEtf_outtime());
    transactionInfoEB.setEtf_interval(transactionInfoDDTO.getEtf_interval());
  }

  public DTOConverter() {
  }

}