package com.chb.coses.deposit.business.facade;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Account Management Interface for legacy compatibility
 */
public interface IAccountManagement extends EJBObject {

    /**
     * Get account information
     * 
     * @param accountNumber account number
     * @return account information
     * @throws RemoteException if remote call fails
     */
    String getAccountInfo(String accountNumber) throws RemoteException;

    /**
     * Get account balance
     * 
     * @param accountNumber account number
     * @return account balance
     * @throws RemoteException if remote call fails
     */
    double getAccountBalance(String accountNumber) throws RemoteException;

    /**
     * Update account balance
     * 
     * @param accountNumber account number
     * @param amount        amount to update
     * @return update result
     * @throws RemoteException if remote call fails
     */
    boolean updateAccountBalance(String accountNumber, double amount) throws RemoteException;
}