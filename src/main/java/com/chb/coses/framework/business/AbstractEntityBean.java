package com.chb.coses.framework.business;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

/**
 * Abstract Entity Bean for legacy compatibility
 */
public abstract class AbstractEntityBean implements EntityBean {

    protected EntityContext entityContext;

    public AbstractEntityBean() {
        // Default constructor
    }

    @Override
    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    @Override
    public void unsetEntityContext() {
        this.entityContext = null;
    }

    @Override
    public void ejbRemove() throws RemoveException {
        // Default implementation
    }

    @Override
    public void ejbActivate() {
        // Default implementation
    }

    @Override
    public void ejbPassivate() {
        // Default implementation
    }

    @Override
    public void ejbLoad() {
        // Default implementation
    }

    @Override
    public void ejbStore() {
        // Default implementation
    }

    /**
     * Get entity context
     * 
     * @return entity context
     */
    protected EntityContext getEntityContext() {
        return entityContext;
    }
}