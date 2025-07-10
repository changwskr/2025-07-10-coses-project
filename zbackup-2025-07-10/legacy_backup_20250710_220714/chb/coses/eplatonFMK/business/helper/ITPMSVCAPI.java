package com.chb.coses.eplatonFMK.business.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import com.chb.coses.eplatonFMK.transfer.*;
import com.chb.coses.framework.transfer.*;
import javax.transaction.*;

// 나중에 이부분은 framework 부분으로 상향한다.
public interface ITPMSVCAPI
{
  public int TPinfo(UserTransaction tx) throws Exception;
  public boolean TPbegin(UserTransaction tx,int second) throws Exception;
  public boolean TPbegin(String second) throws Exception;
  public boolean TPcommit(UserTransaction tx) throws Exception;
  public boolean TProllback(UserTransaction tx) throws Exception;
  public EPlatonEvent TPSsendrecv(EPlatonEvent event) throws Exception;
  public EPlatonEvent TPSsend(EPlatonEvent event) throws Exception;
  public EPlatonEvent TPSrecv(EPlatonEvent event) throws Exception;

}