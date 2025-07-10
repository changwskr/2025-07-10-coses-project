package com.ims.eplaton.eplatonFWK.business;

import com.chb.coses.framework.business.delegate.IBizDelegate;
import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

/**
 * EPlaton Business Delegate Session Bean
 */
public class EPlatonBizDelegateSB implements IBizDelegate {

    private String delegateName;
    private String delegateType;

    public EPlatonBizDelegateSB() {
        this.delegateName = "EPlatonBizDelegate";
        this.delegateType = "SESSION";
    }

    @Override
    public CosesCommonDTO execute(CosesEvent event) throws Exception {
        CosesCommonDTO result = new CosesCommonDTO();

        try {
            if (validateEvent(event)) {
                result = processEvent(event);
            } else {
                result.setResultCode("FAILED");
                result.setResultMessage("Event validation failed");
            }
        } catch (Exception e) {
            result.setResultCode("ERROR");
            result.setResultMessage("Execution error: " + e.getMessage());
        }

        return result;
    }

    @Override
    public boolean validateEvent(CosesEvent event) {
        return event != null && event.getEventData() != null;
    }

    @Override
    public CosesCommonDTO processEvent(CosesEvent event) throws Exception {
        CosesCommonDTO result = new CosesCommonDTO();
        result.setResultCode("SUCCESS");
        result.setResultMessage("Event processed successfully by " + delegateName);
        return result;
    }

    @Override
    public String getDelegateName() {
        return delegateName;
    }

    @Override
    public void setDelegateName(String delegateName) {
        this.delegateName = delegateName;
    }

    public String getDelegateType() {
        return delegateType;
    }

    public void setDelegateType(String delegateType) {
        this.delegateType = delegateType;
    }
}