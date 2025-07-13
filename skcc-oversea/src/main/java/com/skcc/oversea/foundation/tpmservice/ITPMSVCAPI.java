package com.skcc.oversea.foundation.tpmservice;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import javax.transaction.*;

import com.skcc.oversea.eplatonframework.transfer.*;

// 나중에 이부분은 framework 부분으로 상향한다.
public interface ITPMSVCAPI
{
  public int TPinfo(UserTransaction tx) throws Exception;
  public boolean TPbegin(UserTransaction tx,int second) throws Exception;
  public boolean TPbegin(String second) throws Exception;
  public boolean TPcommit(UserTransaction tx) throws Exception;
  public boolean TProllback(UserTransaction tx) throws Exception;
  public EPlatonEvent TPSsend(EPlatonEvent event) throws Exception;
  public EPlatonEvent TPSrecv(EPlatonEvent event) throws Exception;

}
