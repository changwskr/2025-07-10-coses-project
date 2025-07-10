package com.chb.coses.common.business.facade;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Common Management Session Bean Home Interface
 */
public interface CommonManagementSBHome extends EJBHome {

    /**
     * Create Common Management Session Bean
     * 
     * @return Common Management Session Bean
     * @throws CreateException if creation fails
     * @throws RemoteException if remote operation fails
     */
    ICommonManagementSB create() throws CreateException, RemoteException;
}