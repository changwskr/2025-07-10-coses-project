package com.skcc.oversea.common.business.facade;

import com.skcc.oversea.framework.transaction.tpmutil.TPMUtil;
import com.skcc.oversea.deposit.transfer.DepositTransferDTO;
import com.skcc.oversea.foundation.tpmservice.TPSsendrecv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonManagementSBean {

  @Autowired
  private TPMUtil tpmUtil;

  @Autowired
  private TPSsendrecv tpsSendRecv;

  public void processCommonTransaction(String transactionId) {
    // 공통 트랜잭션 처리 로직
    tpmUtil.initializeTPM();
    tpsSendRecv.sendMessage("Transaction: " + transactionId);
  }

  public DepositTransferDTO getDepositInfo(String accountNumber) {
    // 예금 정보 조회 로직
    return new DepositTransferDTO(accountNumber, "SAVINGS", null);
  }
}
