package com.ims.eplaton.eplatonFWK.business;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Home interface for EPlaton Business Delegate Session Bean
 */
public interface EPlatonBizDelegateSBHome extends EJBHome {

    /**
     * Create EPlaton Business Delegate Session Bean
     */
    EPlatonBizDelegateSB create() throws CreateException, RemoteException;

    /**
     * Create EPlaton Business Delegate Session Bean with parameters
     */
    EPlatonBizDelegateSB create(String delegateName) throws CreateException, RemoteException;
}