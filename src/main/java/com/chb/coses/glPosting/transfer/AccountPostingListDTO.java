package com.chb.coses.glPosting.transfer;

import com.chb.coses.framework.transfer.DTO;
import java.util.List;
import java.util.ArrayList;

/**
 * Account Posting List DTO for GL posting operations
 */
public class AccountPostingListDTO extends DTO {

    private List<AccountPostingDTO> postings;
    private String accountNumber;
    private String accountName;
    private String postingDate;
    private String currency;
    private double totalDebit;
    private double totalCredit;
    private double balance;

    public AccountPostingListDTO() {
        super();
        this.postings = new ArrayList<>();
    }

    public List<AccountPostingDTO> getPostings() {
        return postings;
    }

    public void setPostings(List<AccountPostingDTO> postings) {
        this.postings = postings != null ? postings : new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Add posting to the list
     * 
     * @param posting posting to add
     */
    public void addPosting(AccountPostingDTO posting) {
        if (postings == null) {
            postings = new ArrayList<>();
        }
        postings.add(posting);
    }

    /**
     * Calculate totals
     */
    public void calculateTotals() {
        totalDebit = 0.0;
        totalCredit = 0.0;

        if (postings != null) {
            for (AccountPostingDTO posting : postings) {
                if ("DEBIT".equalsIgnoreCase(posting.getType())) {
                    totalDebit += posting.getAmount();
                } else if ("CREDIT".equalsIgnoreCase(posting.getType())) {
                    totalCredit += posting.getAmount();
                }
            }
        }

        balance = totalDebit - totalCredit;
    }

    /**
     * Get posting count
     * 
     * @return posting count
     */
    public int getPostingCount() {
        return postings != null ? postings.size() : 0;
    }

    /**
     * Check if list is empty
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return postings == null || postings.isEmpty();
    }

    /**
     * Inner class for individual posting
     */
    public static class AccountPostingDTO extends DTO {
        private String postingId;
        private String description;
        private String type; // DEBIT or CREDIT
        private double amount;
        private String reference;
        private String postingTime;

        public AccountPostingDTO() {
            super();
        }

        public String getPostingId() {
            return postingId;
        }

        public void setPostingId(String postingId) {
            this.postingId = postingId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getPostingTime() {
            return postingTime;
        }

        public void setPostingTime(String postingTime) {
            this.postingTime = postingTime;
        }
    }
}