package com.skcc.oversea.cashCard.business.cashCard.entity;

import javax.ejb.*;
import java.util.*;
import java.math.*;

public interface HotCardEB extends javax.ejb.EJBLocalObject
{
    public String getCardNumber();
    public int getSequenceNo();
    public void setPrimaryAccountNo(String primaryAccountNo);
    public String getPrimaryAccountNo();
    public void setCifNo(String cifNo);
    public String getCifNo();
    public void setStatus(String status);
    public String getStatus();
    public void setIncidentCode(String incidentCode);
    public String getIncidentCode();
    public void setRegisterDate(String registerDate);
    public String getRegisterDate();
    public void setRegisterTime(String registerTime);
    public String getRegisterTime();
    public void setRegisterBy(String registerBy);
    public String getRegisterBy();
    public void setReleasedDate(String releasedDate);
    public String getReleasedDate();
    public void setReleasedTime(String releasedTime);
    public String getReleasedTime();
    public void setReleasedBy(String releasedBy);
    public String getReleasedBy();
    public void setRemark(String remark);
    public String getRemark();
    public void setCifName(String cifName);
    public String getCifName();
}
