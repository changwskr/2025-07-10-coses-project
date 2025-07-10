package com.ims.eplaton.eplatonFWK.business;

import com.chb.coses.framework.business.delegate.IBizDelegate;
import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;

/**
 * EPlaton Business Delegate Session Bean Implementation
 */
public class EPlatonBizDelegateSBBean implements SessionBean, IBizDelegate {

    private SessionContext sessionContext;
    private String delegateName;
    private String delegateType;

    public EPlatonBizDelegateSBBean() {
        this.delegateName = "EPlatonBizDelegate";
        this.delegateType = "SESSION";
    }

    public void ejbCreate() throws CreateException {
        // Default creation
    }

    public void ejbCreate(String delegateName) throws CreateException {
        this.delegateName = delegateName;
    }

    @Override
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Override
    public void ejbRemove() {
        // Cleanup resources
    }

    @Override
    public void ejbActivate() {
        // Activation logic
    }

    @Override
    public void ejbPassivate() {
        // Passivation logic
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

    public SessionContext getSessionContext() {
        return sessionContext;
    }
}