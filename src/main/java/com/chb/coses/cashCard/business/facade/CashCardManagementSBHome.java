package com.chb.coses.cashCard.business.facade;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * Cash Card Management Home Interface for legacy compatibility
 */
public interface CashCardManagementSBHome extends EJBHome {

    /**
     * Create CashCardManagement instance
     * 
     * @return ICashCardManagement instance
     * @throws CreateException if creation fails
     * @throws RemoteException if remote call fails
     */
    ICashCardManagement create() throws CreateException, RemoteException;
}