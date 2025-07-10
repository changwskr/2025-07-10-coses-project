package com.chb.coses.linkage.transfer;

import com.chb.coses.framework.transfer.DTO;

public class AccountPostingListDTO extends DTO {
    private String accountNo;
    private String postingDate;

    public AccountPostingListDTO() {
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }
}