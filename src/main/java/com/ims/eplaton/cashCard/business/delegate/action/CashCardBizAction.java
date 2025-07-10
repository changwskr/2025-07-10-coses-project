package com.ims.eplaton.cashCard.business.delegate.action;

// java
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import java.lang.reflect.*;

// JNDI
import com.chb.coses.foundation.jndi.JNDIService;

// Framework
import com.chb.coses.cosesFramework.business.delegate.action.CosesBizAction;
import com.chb.coses.framework.business.delegate.action.*;
import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.*;

import com.ims.eplaton.cashCard.business.facade.*;

public class CashCardBizAction extends CosesBizAction
{

    public CashCardBizAction()
    {
    }

    public void act(IEvent event) throws com.chb.coses.framework.exception.BizActionException
    {
        try
        {
            CashCardManagementSBHome cashCardHome =
                    (CashCardManagementSBHome)JNDIService.getInstance()
                    .lookup(CashCardManagementSBHome.class);
            ICashCardManagementSB iCashCardManagementSB = cashCardHome.create();


            this.doAct(iCashCardManagementSB, event);
            /*
            // Method Name을 가져온다.
            String methodName = event.getBizActionMethodName();

            // argument의 type을 가져온다.
            String parameterType = event.getBizActionParameterType();

            // UI에서 넘어오는 CDTO를 가져온다.
            IDTO idto = event.getRequest();

            //event로 넘어온 파라미터 값으로 common에 set을 하려하지만 getCommon()
            //메소드가 없기 때문에 같은 interface를 쓰는 CosesEvent형태로 캐스팅
            //해서 getCommon를 얻어온다.
            CosesCommonDTO common = ((CosesEvent) event).getCommon();

            // event 객체를 통하여 얻은 DTO와 common객체를 paramArray 배열에
            // 담는다.
            Object[] paramArray = {idto, common};

            // System component의 argument의 type(CDTO, CosesCommonDTO)에
            // 해당하는 Class 배열을 만든다.
            // 이 때, CDTO의 class는 bizaction-map에 정의한 type의 이름으로부터
            // dynamic하게 loading한다.
            Class[] typeArray = {Class.forName(parameterType), common.getClass()};


            // 호출할 method를 찾는다.
            Method method = iLending.getClass().getDeclaredMethod(
                    methodName, typeArray);

            // method를 호출하고 return 값을 받는다.
            IDTO outIDTO = (IDTO)method.invoke(iLending, paramArray);
            event.setResponse(outIDTO);*/
        }
        catch(Throwable _e)
        {
            this.throwBizActionException(_e);
        }
    }
}