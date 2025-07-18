package com.chb.coses.user.transfer;

import com.chb.coses.framework.transfer.DTO;

public class UserCDTO extends DTO {
    private String userId;
    private String userName;
    private String email;
    private String status;

    public UserCDTO() {
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
}