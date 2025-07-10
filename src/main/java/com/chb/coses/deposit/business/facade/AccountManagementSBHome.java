package com.chb.coses.deposit.business.facade;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * Account Management Home Interface for legacy compatibility
 */
public interface AccountManagementSBHome extends EJBHome {

    /**
     * Create AccountManagement instance
     * 
     * @return IAccountManagement instance
     * @throws CreateException if creation fails
     * @throws RemoteException if remote call fails
     */
    IAccountManagement create() throws CreateException, RemoteException;
}