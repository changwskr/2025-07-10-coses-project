package com.chb.coses.framework.business.delegate;

import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

public interface IBizDelegate {
    CosesCommonDTO execute(CosesEvent event) throws Exception;

    boolean validateEvent(CosesEvent event);

    CosesCommonDTO processEvent(CosesEvent event) throws Exception;

    String getDelegateName();

    void setDelegateName(String name);
}