package com.ims.oversea.framework.transaction.bean;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.chb.coses.framework.transfer.IDTO;

public abstract class AbstractBIZBCF {

  private EPlatonEvent event;

  public TPSVCINFODTO getTPSVCINFODTO()
  {
    return this.event.getTPSVCINFODTO();
  }
  public EPlatonCommonDTO getEPlatonCommonDTO(){
    return this.event.getCommon();
  }
  public String getSystemName(){
    return getTPSVCINFODTO().getSystem_name();
  }
  public String getOperationClassName(){
    return getTPSVCINFODTO().getOperation_name();
  }
  public String getOperationMethodName(){
    return getTPSVCINFODTO().getOperation_method();
  }
  public String getTransactionNo(){
    return getTPSVCINFODTO().getHostseq();
  }
  public EPlatonEvent getEvent() {
    return event;
  }
  public void setEvent(EPlatonEvent event) {
    this.event = event;
  }
  public IDTO getRequestCDTO() {
    return this.event.getRequest();
  }
  public IDTO getResponseCDTO() {
    return this.event.getResponse();
  }
  protected void printf(int mode,String contentToWrite)
  {
    LOGEJ.getInstance().printf(mode,this.event,contentToWrite);
  }

}


