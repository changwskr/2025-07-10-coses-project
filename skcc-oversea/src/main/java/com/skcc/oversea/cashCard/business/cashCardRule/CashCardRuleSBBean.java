
package com.skcc.oversea.cashCard.business.cashCardRule;

import com.skcc.oversea.foundation.security.SecurityManager;
import com.skcc.oversea.framework.transfer.CosesFrameworkTransferDTO;
import com.skcc.oversea.framework.transfer.SystemParameterCDTO;
import com.skcc.oversea.framework.transfer.ModifyDTO;
import com.skcc.oversea.reference.transfer.ReferenceTransferDTO;
import com.skcc.oversea.common.transfer.CommonTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashCardRuleSBBean {

    @Autowired
    private SecurityManager securityManager;

    public SystemParameterCDTO getSystemParameter(String parameterId) {
        // 시스템 파라미터 조회 로직
        return new SystemParameterCDTO(parameterId, "PARAM_NAME", "PARAM_VALUE");
    }

    public ModifyDTO modifyCashCardRule(String ruleId) {
        // 현금카드 규칙 수정 로직
        return new ModifyDTO(ruleId, "MODIFY");
    }

    public ModifyDTO deleteCashCardRule(String ruleId) {
        // 현금카드 규칙 삭제 로직
        return new ModifyDTO(ruleId, "DELETE");
    }
}
