package com.chb.coses.eplatonFramework.business.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.chb.coses.eplatonFramework.transfer.*;
import com.chb.coses.eplatonFramework.business.model.TransactionLogDDTO;

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

    //-------------------------------------------------------------------------*
    // ...... 추가로직구성

    return transactionLogDDTO;
  }



}