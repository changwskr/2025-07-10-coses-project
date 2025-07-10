package com.chb.coses.reference.business.facade;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Reference Management Session Bean Home Interface
 */
public interface ReferenceManagementSBHome extends EJBHome {

    /**
     * Create Reference Management Session Bean
     * 
     * @return Reference Management Session Bean
     * @throws CreateException if creation fails
     * @throws RemoteException if remote operation fails
     */
    IReferenceManagementSB create() throws CreateException, RemoteException;
}