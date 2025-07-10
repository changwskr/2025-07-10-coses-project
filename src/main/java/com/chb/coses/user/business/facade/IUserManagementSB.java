package com.chb.coses.user.business.facade;

import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;
import com.chb.coses.user.transfer.UserDTO;

/**
 * Interface for User Management Session Bean
 */
public interface IUserManagementSB {

    /**
     * Create new user
     */
    CosesCommonDTO createUser(CosesEvent event) throws Exception;

    /**
     * Update user information
     */
    CosesCommonDTO updateUser(CosesEvent event) throws Exception;

    /**
     * Delete user
     */
    CosesCommonDTO deleteUser(CosesEvent event) throws Exception;

    /**
     * Get user by ID
     */
    UserDTO getUserById(String userId) throws Exception;

    /**
     * Get all users
     */
    CosesCommonDTO getAllUsers(CosesEvent event) throws Exception;

    /**
     * Authenticate user
     */
    CosesCommonDTO authenticateUser(CosesEvent event) throws Exception;
}