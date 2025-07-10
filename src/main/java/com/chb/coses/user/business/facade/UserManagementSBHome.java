package com.chb.coses.user.business.facade;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * User Management Home Interface for legacy compatibility
 */
public interface UserManagementSBHome extends EJBHome {

    /**
     * Create UserManagement instance
     * 
     * @return IUserManagement instance
     * @throws CreateException if creation fails
     * @throws RemoteException if remote call fails
     */
    IUserManagement create() throws CreateException, RemoteException;
}