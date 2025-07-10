package com.ims.oversea.framework.transaction.txmonitor.business.thing.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import com.ims.oversea.framework.transaction.txmonitor.business.thing.model.TransactionInfoDDTO;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.entity.transactioninfo.*;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;
import com.chb.coses.foundation.jndi.*;


public class EJBUtilThing
{
    public static TransactionInfoEBHome getTransactionInfoEBHome()
    {
        TransactionInfoEBHome transactionInfoEBHome = null;

        try
        {
            transactionInfoEBHome = (TransactionInfoEBHome)
                    JNDIService.getInstance().lookup(
                    JNDINamesThing.TRANSACTIONINFO_EB_HOME);
        }
        catch (NamingException nEx)
        {
            throw new EJBException(nEx);
        }
        return transactionInfoEBHome;
    }

}
