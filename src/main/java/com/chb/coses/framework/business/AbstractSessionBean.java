package com.chb.coses.framework.business;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;

/**
 * Abstract base class for Session Beans
 */
public abstract class AbstractSessionBean implements SessionBean {

    protected SessionContext sessionContext;

    public AbstractSessionBean() {
        // Default constructor
    }

    @Override
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Override
    public void ejbRemove() {
        // Default cleanup implementation
    }

    @Override
    public void ejbActivate() {
        // Default activation implementation
    }

    @Override
    public void ejbPassivate() {
        // Default passivation implementation
    }

    /**
     * Get session context
     */
    public SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * Check if session is valid
     */
    protected boolean isSessionValid() {
        return sessionContext != null;
    }

    /**
     * Get caller principal
     */
    protected String getCallerPrincipal() {
        if (sessionContext != null && sessionContext.getCallerPrincipal() != null) {
            return sessionContext.getCallerPrincipal().getName();
        }
        return null;
    }

    /**
     * Check if caller is in role
     */
    protected boolean isCallerInRole(String role) {
        return sessionContext != null && sessionContext.isCallerInRole(role);
    }
}