package com.skcc.oversea.framework.transfer;

import java.io.Serializable;
import java.util.List;

public class AccountPostingListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<AccountPostingDTO> accountPostings;
    private int totalCount;
    private String status;
    private String message;

    public AccountPostingListDTO() {
    }

    public AccountPostingListDTO(List<AccountPostingDTO> accountPostings) {
        this.accountPostings = accountPostings;
        this.totalCount = accountPostings != null ? accountPostings.size() : 0;
    }

    // Getters and Setters
    public List<AccountPostingDTO> getAccountPostings() {
        return accountPostings;
    }

    public void setAccountPostings(List<AccountPostingDTO> accountPostings) {
        this.accountPostings = accountPostings;
        this.totalCount = accountPostings != null ? accountPostings.size() : 0;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}