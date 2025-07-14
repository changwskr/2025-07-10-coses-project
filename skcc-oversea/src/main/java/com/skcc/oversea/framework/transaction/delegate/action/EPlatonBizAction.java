package com.skcc.oversea.framework.transaction.delegate.action;

import com.skcc.oversea.framework.transaction.tpmutil.TPMUtil;
import com.skcc.oversea.framework.exception.BizActionException;
import com.skcc.oversea.foundation.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EPlatonBizAction {

  @Autowired
  private TPMUtil tpmUtil;

  public void processBusinessAction(String actionId) throws BizActionException {
    try {
      // 비즈니스 액션 처리 로직
      tpmUtil.initializeTPM();

      // 성공 처리
      String status = Constants.TXN_STATUS_SUCCESS;

    } catch (Exception e) {
      // 실패 처리
      String status = Constants.TXN_STATUS_FAILED;
      throw new BizActionException("Business action failed", e);
    }
  }
}
