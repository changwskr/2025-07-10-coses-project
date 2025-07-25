﻿package com.ims.eplaton.eplatonFKC.business.helper;

import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import com.chb.coses.foundation.jndi.JNDIService;
import com.chb.coses.reference.business.facade.IReferenceManagementSB;
import com.chb.coses.reference.business.facade.ReferenceManagementSBHome;
import com.chb.coses.common.business.facade.ICommonManagementSB;
import com.chb.coses.common.business.facade.CommonManagementSBHome;
import com.chb.coses.user.business.facade.IUserManagement;
import com.chb.coses.user.business.facade.UserManagementSBHome;

/**
 * Enterprise JavaBean를 생성해서 돌려준다.
 *
 * @author  <a href="mailto:ghyu@imssystem.com">Gwanghyeok Yu</a>
 * @version 1.0, 2002/09/03
 */
public class EJBUtils
{
    public static ICommonManagementSB getCommonEJB()
    {
        try {
            CommonManagementSBHome home =
                    (CommonManagementSBHome)
                    JNDIService.getInstance().lookup(
                    CommonManagementSBHome.class.getName());
            return home.create();
        } catch (NamingException e) {
            throw new EJBException(e);
        } catch (RemoteException e) {
            throw new EJBException(e);
        } catch (CreateException e) {
            throw new EJBException(e);
        }
    }

    public static IReferenceManagementSB getReferenceEJB()
    {
        try {
            ReferenceManagementSBHome home =
                    (ReferenceManagementSBHome)
                    JNDIService.getInstance().lookup(
                    ReferenceManagementSBHome.class.getName());
            return home.create();
        } catch (NamingException e) {
            throw new EJBException(e);
        } catch (RemoteException e) {
            throw new EJBException(e);
        } catch (CreateException e) {
            throw new EJBException(e);
        }
    }

    public static IUserManagement getUserEJB()
    {
        try {
            UserManagementSBHome home =
                    (UserManagementSBHome)
                    JNDIService.getInstance().lookup(
                    UserManagementSBHome.class.getName());
            return home.create();
        } catch (NamingException e) {
            throw new EJBException(e);
        } catch (RemoteException e) {
            throw new EJBException(e);
        } catch (CreateException e) {
            throw new EJBException(e);
        }
    }
}
