package com.ims.eplaton.framework.tcf;

import java.rmi.*;

import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.transfer.*;

// 나중에 이부분은 framework 부분으로 상향한다.
/**
 *
 * Business TCF가 구현해야 하는 interface.
 */
public interface ITCF_test
{

    /**
     * Application layer의 request를 받아서 처리하는 operation <br>
     *
     * @param event Application layer와 business layer 사이에 주고 받는 정보를 담는 객체
     * @return IEvent call-by value 방식으로 정보를 전달하므로 IEvent type을 다시 리턴한다.
     * @throws RemoteException
     * @throws BizDelegateException
     */

    public abstract IEvent STF(IEvent event) throws RemoteException, BizDelegateException;
    public abstract IEvent ROUTE(IEvent event) throws RemoteException, BizDelegateException;
    public abstract IEvent ETF(IEvent event) throws RemoteException, BizDelegateException;

}
