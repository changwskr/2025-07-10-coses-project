package com.chb.coses.user.business.facade;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * User Management Interface for legacy compatibility
 */
public interface IUserManagement extends EJBObject {

    /**
     * Get user information
     * 
     * @param userId user identifier
     * @return user information
     * @throws RemoteException if remote call fails
     */
    String getUserInfo(String userId) throws RemoteException;

    /**
     * Validate user
     * 
     * @param userId   user identifier
     * @param password user password
     * @return validation result
     * @throws RemoteException if remote call fails
     */
    boolean validateUser(String userId, String password) throws RemoteException;
}