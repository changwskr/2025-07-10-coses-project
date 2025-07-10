package com.ims.oversea.framework.transfer;

import java.util.Map;
import java.util.HashMap;
import com.chb.coses.foundation.utility.*;
import com.chb.coses.framework.transfer.IDTO;

/**
 * @version 1.30
 *
 * 우리 시스템의 TCFDTO의 최상위 class로 모든 TCFDTO가 구현해야 하는 공통 method를 구현한다.
 */
public abstract class TCFDTO implements IDTO
{
  private Map dtoProperties;
  private String system;
  private String method;
  private String transaction_no;

  /**
   * @see java.lang.Object#Object()
   */
  public TCFDTO()
  {
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
    return Reflector.objectToString(this);
  }

  private Map getTCFDTOProperties() {
    if (dtoProperties == null) {
      dtoProperties = new HashMap(2);
    }
    return dtoProperties;
  }

  public final void clearTCFDTOProperty() {
    if (dtoProperties != null) {
      dtoProperties.clear();
    }
  }

  public final Object getTCFDTOProperty(Object key) {
    if (dtoProperties == null) {
      return null;
    } else {
      return getTCFDTOProperties().get(key);
    }
  }

  public final void putTCFDTOProperty(Object key, Object value) {
    getTCFDTOProperties().put(key, value);
  }
  public String getMethod() {
    return method;
  }
  public String getSystem() {
    return system;
  }
  public String getTransaction_no() {
    return transaction_no;
  }
  public void setTransaction_no(String transaction_no) {
    this.transaction_no = transaction_no;
  }
  public void setSystem(String system) {
    this.system = system;
  }
  public void setMethod(String method) {
    this.method = method;
  }
}