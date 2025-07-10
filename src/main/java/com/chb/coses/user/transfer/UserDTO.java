package com.chb.coses.user.transfer;

import com.chb.coses.framework.transfer.DTO;

/**
 * User Data Transfer Object
 */
public class UserDTO extends DTO {

    private String userId;
    private String userName;
    private String userType;
    private String email;
    private String status;
    private String department;

    public UserDTO() {
        super();
    }

    public UserDTO(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}