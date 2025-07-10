package com.ims.eplaton.cashCard.business.cashCard.entity;

import javax.ejb.*;

abstract public class HotCardEBBean extends com.chb.coses.framework.business.AbstractEntityBean implements EntityBean
{
    public HotCardEBPK ejbCreate(java.lang.String cardNumber, int sequenceNo) throws CreateException {
        setCardNumber(cardNumber);
        setSequenceNo(sequenceNo);
        return null;
    }
    public void ejbPostCreate(java.lang.String cardNumber, int sequenceNo) throws CreateException {
        /**@todo Complete this method*/
    }
    public abstract void setCardNumber(java.lang.String cardNumber);
    public abstract void setSequenceNo(int sequenceNo);
    public abstract void setPrimaryAccountNo(java.lang.String primaryAccountNo);
    public abstract void setCifNo(java.lang.String cifNo);
    public abstract void setStatus(java.lang.String status);
    public abstract void setIncidentCode(java.lang.String incidentCode);
    public abstract void setRegisterDate(java.lang.String registerDate);
    public abstract void setRegisterTime(java.lang.String registerTime);
    public abstract void setRegisterBy(java.lang.String registerBy);
    public abstract void setReleasedDate(java.lang.String releasedDate);
    public abstract void setReleasedTime(java.lang.String releasedTime);
    public abstract void setReleasedBy(java.lang.String releasedBy);
    public abstract void setRemark(java.lang.String remark);
    public abstract void setCifName(java.lang.String cifName);
    public abstract java.lang.String getCardNumber();
    public abstract int getSequenceNo();
    public abstract java.lang.String getPrimaryAccountNo();
    public abstract java.lang.String getCifNo();
    public abstract java.lang.String getStatus();
    public abstract java.lang.String getIncidentCode();
    public abstract java.lang.String getRegisterDate();
    public abstract java.lang.String getRegisterTime();
    public abstract java.lang.String getRegisterBy();
    public abstract java.lang.String getReleasedDate();
    public abstract java.lang.String getReleasedTime();
    public abstract java.lang.String getReleasedBy();
    public abstract java.lang.String getRemark();
    public abstract java.lang.String getCifName();
}