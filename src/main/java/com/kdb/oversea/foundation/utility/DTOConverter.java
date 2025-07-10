package com.kdb.oversea.foundation.utility;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.kdb.oversea.eplatonframework.transfer.*;
import com.chb.coses.framework.transfer.*;
import com.kdb.oversea.framework.transaction.model.TransactionUpDownDDTO;
import com.kdb.oversea.framework.transaction.model.TransactionLogDDTO;


public class DTOConverter
{

  public DTOConverter()
  {
  }

  public static TransactionLogDDTO getTransactionLogDDTO(EPlatonEvent event)
  {
    EPlatonCommonDTO commonDTO=null;
    TPSVCINFODTO tpsvcinfo=null;
    TPMSVCINFO tpmsvcinfo=null;

    commonDTO = (EPlatonCommonDTO)event.getCommon();
    tpsvcinfo = event.getTPSVCINFODTO();

    String transactionId = tpsvcinfo.getBp_sequence();
    String hostName      = CommonUtil.GetHostName();
    String bankCode      = commonDTO.getBankCode();
    String branchCode    = commonDTO.getBranchCode();
    String userId        = commonDTO.getUserID();
    String channelType   = commonDTO.getChannelType();
    String businessDate  = commonDTO.getBusinessDate();
    String eventNo       = commonDTO.getEventNo();
    String action        = tpsvcinfo.getReqName();
    String IPAddress     = commonDTO.getIPAddress();


    TransactionLogDDTO transactionLogDDTO = new TransactionLogDDTO();
    int index = action.indexOf('-');
    transactionLogDDTO.setTransactionId(transactionId);
    transactionLogDDTO.setHostName(hostName);
    transactionLogDDTO.setSystemName(action.substring(0, index));
    transactionLogDDTO.setMethodName(action.substring(index + 1));
    transactionLogDDTO.setBankCode(bankCode);
    transactionLogDDTO.setBranchCode(branchCode);
    if(userId==null){
        userId = "other";
    }
    transactionLogDDTO.setUserId(userId);
    transactionLogDDTO.setChannelType(channelType);
    if(businessDate==null){
        businessDate = "yyyymmdd";
    }
    transactionLogDDTO.setBusinessDate(businessDate);
    transactionLogDDTO.setEventNo(eventNo);
    transactionLogDDTO.setIPAddress(IPAddress);

    return transactionLogDDTO;
  }


  public static TransactionUpDownDDTO getTransactionUpDownDDTO(EPlatonEvent event)
  {
    EPlatonCommonDTO commonDTO=null;
    TPSVCINFODTO tpsvcinfoDTO=null;
    TPMSVCINFO tpmsvcinfo=null;

    commonDTO = (EPlatonCommonDTO)event.getCommon();
    tpsvcinfoDTO = event.getTPSVCINFODTO();

    TransactionUpDownDDTO transactionUPDOWNDDTO = new TransactionUpDownDDTO();

    transactionUPDOWNDDTO.setBankCode(commonDTO.getBankCode());
    transactionUPDOWNDDTO.setBaseCurrency(commonDTO.getBaseCurrency());
    transactionUPDOWNDDTO.setBranchCode(commonDTO.getBranchCode());
    transactionUPDOWNDDTO.setBusinessDate(commonDTO.getBusinessDate());
    transactionUPDOWNDDTO.setChannelType(commonDTO.getChannelType());
    transactionUPDOWNDDTO.setEventNo(commonDTO.getEventNo());
    transactionUPDOWNDDTO.setFxRateCount(commonDTO.getFxRateCount());
    transactionUPDOWNDDTO.setGlPostBranchCode(commonDTO.getGlPostBranchCode());
    transactionUPDOWNDDTO.setIPAddress(commonDTO.getIPAddress());
    transactionUPDOWNDDTO.setMultiPL(commonDTO.getMultiPL());
    transactionUPDOWNDDTO.setSystemInTime(commonDTO.getSystemInTime());
    transactionUPDOWNDDTO.setSystemDate(commonDTO.getSystemDate());
    transactionUPDOWNDDTO.setReqName(commonDTO.getReqName());
    transactionUPDOWNDDTO.setRegionCode(commonDTO.getRegionCode());
    transactionUPDOWNDDTO.setTerminalType(commonDTO.getTerminalType());
    transactionUPDOWNDDTO.setTransactionNo(commonDTO.getTransactionNo());
    transactionUPDOWNDDTO.setUserID(commonDTO.getUserID());
    transactionUPDOWNDDTO.setUserLevel(commonDTO.getUserLevel());
    transactionUPDOWNDDTO.setXmlSeq(commonDTO.getXmlSeq());
    transactionUPDOWNDDTO.setTimeZone(commonDTO.getTimeZone());
    transactionUPDOWNDDTO.setTerminalID(commonDTO.getTerminalID());
    transactionUPDOWNDDTO.setSystemOutTime(commonDTO.getSystemOutTime());
    transactionUPDOWNDDTO.setNation(commonDTO.getNation());

    transactionUPDOWNDDTO.setAction_name(tpsvcinfoDTO.getAction_name());
    transactionUPDOWNDDTO.setCdto_name(tpsvcinfoDTO.getCdto_name());
    transactionUPDOWNDDTO.setError_message(tpsvcinfoDTO.getError_message());
    transactionUPDOWNDDTO.setErrorcode(tpsvcinfoDTO.getErrorcode());
    transactionUPDOWNDDTO.setHostseq(tpsvcinfoDTO.getHostseq());
    transactionUPDOWNDDTO.setOperation_method_name(tpsvcinfoDTO.getOperation_method());
    transactionUPDOWNDDTO.setOperation_name(tpsvcinfoDTO.getOperation_name());
    transactionUPDOWNDDTO.setOrgseq(tpsvcinfoDTO.getOrgseq());
    transactionUPDOWNDDTO.setRequest_cdto(tpsvcinfoDTO.getCdto_name());
    transactionUPDOWNDDTO.setSystem_name(tpsvcinfoDTO.getSystem_name());
    transactionUPDOWNDDTO.setSystem_date(tpsvcinfoDTO.getSystem_date());
    transactionUPDOWNDDTO.setTpfq(tpsvcinfoDTO.getTpfq());
    transactionUPDOWNDDTO.setTrclass(tpsvcinfoDTO.getTrclass());
    transactionUPDOWNDDTO.setTx_timer(tpsvcinfoDTO.getTx_timer());
    transactionUPDOWNDDTO.setBp_hostseq(tpsvcinfoDTO.getBp_sequence() );
    transactionUPDOWNDDTO.setWeb_intime(tpsvcinfoDTO.getWeb_intime());
    transactionUPDOWNDDTO.setWeb_outtime(tpsvcinfoDTO.getWeb_outtime());
    transactionUPDOWNDDTO.setWeb_timeout(tpsvcinfoDTO.getWeb_timeout());

    IDTO reqcdto =  event.getRequest();
    IDTO rescdto = event.getResponse();

    if( reqcdto != null ){
      transactionUPDOWNDDTO.setRequest_cdto(com.chb.coses.foundation.utility.Reflector.objectToString(event.getRequest()));
    }
    if( rescdto != null ){
      transactionUPDOWNDDTO.setResponse_cdto(com.chb.coses.foundation.utility.Reflector.objectToString(event.getResponse()));
    }

    return transactionUPDOWNDDTO;
  }


}