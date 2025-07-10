package com.chb.coses.cashCard.business.facade;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Cash Card Management Interface for legacy compatibility
 */
public interface ICashCardManagement extends EJBObject {

    /**
     * Get card information
     * 
     * @param cardNumber card number
     * @return card information
     * @throws RemoteException if remote call fails
     */
    String getCardInfo(String cardNumber) throws RemoteException;

    /**
     * Get card balance
     * 
     * @param cardNumber card number
     * @return card balance
     * @throws RemoteException if remote call fails
     */
    double getCardBalance(String cardNumber) throws RemoteException;

    /**
     * Update card balance
     * 
     * @param cardNumber card number
     * @param amount     amount to update
     * @return update result
     * @throws RemoteException if remote call fails
     */
    boolean updateCardBalance(String cardNumber, double amount) throws RemoteException;

    /**
     * Block card
     * 
     * @param cardNumber card number
     * @return block result
     * @throws RemoteException if remote call fails
     */
    boolean blockCard(String cardNumber) throws RemoteException;

    /**
     * Unblock card
     * 
     * @param cardNumber card number
     * @return unblock result
     * @throws RemoteException if remote call fails
     */
    boolean unblockCard(String cardNumber) throws RemoteException;
}