package com.ims.eplaton.foundation.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.eplatonFWK.business.model.TransactionLogDDTO;

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

    TransactionLogDDTO transactionLogDDTO = new TransactionLogDDTO();

    transactionLogDDTO.setAction_name(tpsvcinfo.getAction_name());
    transactionLogDDTO.setBankCode(commonDTO.getBankCode());
    transactionLogDDTO.setBranchCode(commonDTO.getBranchCode());
    transactionLogDDTO.setBusinessDate(commonDTO.getBusinessDate());
    transactionLogDDTO.setCall_hostseq(tpsvcinfo.getHostseq());
    transactionLogDDTO.setCall_orgseq(tpsvcinfo.getOrgseq() );
    transactionLogDDTO.setError_code(tpsvcinfo.getErrorcode() );
    transactionLogDDTO.setSystem_date(commonDTO.getSystemDate() );
    transactionLogDDTO.setSystemInTime(commonDTO.getSystemInTime() );
    transactionLogDDTO.setSystemOutTime(commonDTO.getSystemOutTime() );
    transactionLogDDTO.setHostseq(tpsvcinfo.getHostseq() );
    transactionLogDDTO.setOrgseq(tpsvcinfo.getOrgseq());

    transactionLogDDTO.setTransactionId(tpsvcinfo.getHostseq() );
    transactionLogDDTO.setTransactionNo(tpsvcinfo.getHostseq() );
    transactionLogDDTO.setMethodName(commonDTO.getReqName());

    transactionLogDDTO.setSystem_name(tpsvcinfo.getSystem_name() );
    transactionLogDDTO.setSystemName(tpsvcinfo.getSystem_name());

    transactionLogDDTO.setHostName(CommonUtil.GetHostName());

    transactionLogDDTO.setAction_name(tpsvcinfo.getAction_name());
    transactionLogDDTO.setBankCode(commonDTO.getBankCode());
    transactionLogDDTO.setBranchCode(commonDTO.getBranchCode());
    transactionLogDDTO.setBusinessDate(commonDTO.getBusinessDate());
    transactionLogDDTO.setCall_hostseq(tpsvcinfo.getHostseq());

    transactionLogDDTO.setEventNo(commonDTO.getEventNo() );
    transactionLogDDTO.setChannelType(commonDTO.getChannelType() );
    transactionLogDDTO.setIPAddress(commonDTO.getIPAddress());
    transactionLogDDTO.setUserId(commonDTO.getUserID() );

    transactionLogDDTO.setBranchCode(commonDTO.getBranchCode() );
    transactionLogDDTO.setError_code(tpsvcinfo.getErrorcode() );
    transactionLogDDTO.setErrorCode(tpsvcinfo.getErrorcode());
    transactionLogDDTO.setErrorcode(tpsvcinfo.getErrorcode());

    //-------------------------------------------------------------------------*
    // ...... 추가로직구성

    return transactionLogDDTO;
  }



}