package com.skcc.oversea.eplatonframework.transfer;

import java.math.BigDecimal;
import com.skcc.oversea.framework.constants.Constants;
import com.skcc.oversea.framework.transfer.ListDTO;
import com.skcc.oversea.framework.transfer.AccountPostingListDTO;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 * 2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 * 
 * @author : 장우승(WooSungJang)
 * @company: IMS SYSTEM
 * @email : changwskr@yahoo.co.kr
 * @version 1.0
 *          =============================================================================
 */

public class EPLcommonCDTO extends com.skcc.oversea.framework.transfer.DTO {
  private String accountNumber;
  private String bankCode;
  private String branchCode;
  private String accountGroup;
  private String accountGroupKind;
  private String CIFName;
  private String CIFNo;
  private String openDate;
  private String currency;
  private BigDecimal grossBalance = Constants.ZERO;
  private BigDecimal holdBalance = Constants.ZERO;
  private String lastCreditDate;
  private BigDecimal lastCreditAmount = Constants.ZERO;
  private String lastDebitDate;
  private BigDecimal lastDebitAmount = Constants.ZERO;
  private BigDecimal collectedBalance = Constants.ZERO;
  private BigDecimal memoBalance = Constants.ZERO;
  private String status;
  private BigDecimal uncollectedBalance = Constants.ZERO;
  private String feeCurrency;
  private BigDecimal feeAmount = Constants.ZERO;
  private String remarks;
  private String glCode;
  private String expireDate;
  private String interestIndex;
  private java.math.BigDecimal interestRate = Constants.ZERO;
  private BigDecimal tempBalance = Constants.ZERO;
  private int lastPbLine = 0;
  private int pageNumber;
  private int linePerPage;
  private BigDecimal passbookBalance = Constants.ZERO;
  private AccountPostingListDTO accountPostingListDTO;
  private StateVoucherImageCDTO stateVoucherImageCDTO;
  private int sequenceNumber;
  private String oldAccountNumber;
  private String securityNo;
  private String issueKind;
  private String openBranchName;
  private String openBranchTelNo;
  private String idNo;
  private BigDecimal beforeInterestRate = Constants.ZERO;
  private String trxNo;
  private String passbookNo;
  private int count;
  private String secondaryAccount;
  private int endLine;
  private String intCalcStrtdate;
  private String pbKind;
  private String checkNumber;
  private int lastPbLine1;
  private String inclusionFee;
  private String calculationFee;
  private String pbIssueKind;
  private int pbIssueCount;
  private String pbMsVersionPb;
  private String pbMsVersionDb;
  private int pbLinePerPage;
  private int pbSkipPerPage;
  private String renewDate;
  private String commonCode;
  private String commonName;
  private String commonType;
  private String commonValue;
  private String description;
  private Boolean isActive;
  private String effectiveDate;
  private String expiryDate;

  public EPLcommonCDTO() {
    accountNumber = "*";
    bankCode = "*";
    branchCode = "*";
    accountGroup = "*";
    accountGroupKind = "*";
    CIFName = "*";
    CIFNo = "*";
    openDate = "*";
    currency = "*";
    grossBalance = Constants.ZERO;
    holdBalance = Constants.ZERO;
    lastCreditDate = "*";
    lastCreditAmount = Constants.ZERO;
    lastDebitDate = "*";
    lastDebitAmount = Constants.ZERO;
    collectedBalance = Constants.ZERO;
    memoBalance = Constants.ZERO;
    status = "*";
    uncollectedBalance = Constants.ZERO;
    feeCurrency = "*";
    feeAmount = Constants.ZERO;
    remarks = "*";
    glCode = "*";
    expireDate = "*";
    interestIndex = "*";
    java.math.BigDecimal interestRate = Constants.ZERO;
    tempBalance = Constants.ZERO;
    int lastPbLine = 0;
    int pageNumber = 0;
    int linePerPage = 0;
    passbookBalance = Constants.ZERO;
    AccountPostingListDTO accountPostingListDTO;
    StateVoucherImageCDTO stateVoucherImageCDTO;
    int sequenceNumber = 0;
    oldAccountNumber = "*";
    securityNo = "*";
    issueKind = "*";
    openBranchName = "*";
    openBranchTelNo = "*";
    idNo = "*";
    beforeInterestRate = Constants.ZERO;
    trxNo = "*";
    passbookNo = "*";
    int count = 0;
    secondaryAccount = "*";
    int endLine = 0;
    intCalcStrtdate = "*";
    pbKind = "*";
    checkNumber = "*";
    int lastPbLine1 = 0;
    inclusionFee = "*";
    calculationFee = "*";
    pbIssueKind = "*";
    int pbIssueCount = 0;
    pbMsVersionPb = "*";
    pbMsVersionDb = "*";
    int pbLinePerPage = 0;
    int pbSkipPerPage = 0;
    renewDate = "*";
    commonCode = "*";
    commonName = "*";
    commonType = "*";
    commonValue = "*";
    description = "*";
    isActive = false;
    effectiveDate = "*";
    expiryDate = "*";

  }

  public String getCheckNumber() {
    return checkNumber;
  }

  public void setCheckNumber(String checkNumber) {
    this.checkNumber = checkNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getBankCode() {
    return bankCode;
  }

  public String getAccountGroup() {
    return accountGroup;
  }

  public java.math.BigDecimal getCollectedBalance() {
    return collectedBalance;
  }

  public String getCurrency() {
    return currency;
  }

  public String getCIFNo() {
    return CIFNo;
  }

  public java.math.BigDecimal getGrossBalance() {
    return grossBalance;
  }

  public java.math.BigDecimal getHoldBalance() {
    return holdBalance;
  }

  public java.math.BigDecimal getMemoBalance() {
    return memoBalance;
  }

  public String getOpenDate() {
    return openDate;
  }

  public String getRenewDate() {
    return renewDate;
  }

  public String getStatus() {
    return status;
  }

  public java.math.BigDecimal getUncollectedBalance() {
    return uncollectedBalance;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public void setAccountGroup(String accountGroup) {
    this.accountGroup = accountGroup;
  }

  public void setCollectedBalance(java.math.BigDecimal collectedBalance) {
    this.collectedBalance = collectedBalance;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setCIFNo(String CIFNo) {
    this.CIFNo = CIFNo;
  }

  public void setGrossBalance(java.math.BigDecimal grossBalance) {
    this.grossBalance = grossBalance;
  }

  public void setHoldBalance(java.math.BigDecimal holdBalance) {
    this.holdBalance = holdBalance;
  }

  public void setMemoBalance(java.math.BigDecimal memoBalance) {
    this.memoBalance = memoBalance;
  }

  public void setOpenDate(String openDate) {
    this.openDate = openDate;
  }

  public void setRenewDate(String renewDate) {
    this.renewDate = renewDate;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setUncollectedBalance(java.math.BigDecimal uncollectedBalance) {
    this.uncollectedBalance = uncollectedBalance;
  }

  public void setCIFName(String CIFName) {
    this.CIFName = CIFName;
  }

  public String getCIFName() {
    return CIFName;
  }

  public void setLastDebitDate(String lastDebitDate) {
    this.lastDebitDate = lastDebitDate;
  }

  public String getLastDebitDate() {
    return lastDebitDate;
  }

  public void setLastCreditDate(String lastCreditDate) {
    this.lastCreditDate = lastCreditDate;
  }

  public String getLastCreditDate() {
    return lastCreditDate;
  }

  public String getAccountGroupKind() {
    return accountGroupKind;
  }

  public void setAccountGroupKind(String accountGroupKind) {
    this.accountGroupKind = accountGroupKind;
  }

  public String getBranchCode() {
    return branchCode;
  }

  public void setBranchCode(String branchCode) {
    this.branchCode = branchCode;
  }

  public BigDecimal getFeeAmount() {
    return feeAmount;
  }

  public String getFeeCurrency() {
    return feeCurrency;
  }

  public void setFeeAmount(BigDecimal feeAmount) {
    this.feeAmount = feeAmount;
  }

  public void setFeeCurrency(String feeCurrency) {
    this.feeCurrency = feeCurrency;
  }

  public BigDecimal getLastCreditAmount() {
    return lastCreditAmount;
  }

  public void setLastCreditAmount(BigDecimal lastCreditAmount) {
    this.lastCreditAmount = lastCreditAmount;
  }

  public BigDecimal getLastDebitAmount() {
    return lastDebitAmount;
  }

  public void setLastDebitAmount(BigDecimal lastDebitAmount) {
    this.lastDebitAmount = lastDebitAmount;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getGlCode() {
    return glCode;
  }

  public void setGlCode(String glCode) {
    this.glCode = glCode;
  }

  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }

  public String getExpireDate() {
    return expireDate;
  }

  public void setInterestIndex(String interestIndex) {
    this.interestIndex = interestIndex;
  }

  public String getInterestIndex() {
    return interestIndex;
  }

  public void setInterestRate(java.math.BigDecimal interestRate) {
    this.interestRate = interestRate;
  }

  public java.math.BigDecimal getInterestRate() {
    return interestRate;
  }

  public AccountPostingListDTO getAccountPostingListDTO() {
    return accountPostingListDTO;
  }

  public void setAccountPostingListDTO(AccountPostingListDTO accountPostingListDTO) {
    this.accountPostingListDTO = accountPostingListDTO;
  }

  public StateVoucherImageCDTO getStateVoucherImageCDTO() {
    return stateVoucherImageCDTO;
  }

  public void setStateVoucherImageCDTO(StateVoucherImageCDTO stateVoucherImageCDTO) {
    this.stateVoucherImageCDTO = stateVoucherImageCDTO;
  }

  public int getLastPbLine() {
    return lastPbLine;
  }

  public int getLastPbLine1() {
    return lastPbLine1;
  }

  public int getLinePerPage() {
    return linePerPage;
  }

  public void setLastPbLine(int lastPbLine) {
    this.lastPbLine = lastPbLine;
  }

  public void setLastPbLine1(int lastPbLine1) {
    this.lastPbLine1 = lastPbLine1;
  }

  public void setLinePerPage(int linePerPage) {
    this.linePerPage = linePerPage;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public BigDecimal getTempBalance() {
    return tempBalance;
  }

  public void setTempBalance(BigDecimal tempBalance) {
    this.tempBalance = tempBalance;
  }

  public BigDecimal getPassbookBalance() {
    return passbookBalance;
  }

  public void setPassbookBalance(BigDecimal passbookBalance) {
    this.passbookBalance = passbookBalance;
  }

  public void setSequenceNumber(int sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
  }

  public int getSequenceNumber() {
    return sequenceNumber;
  }

  public String getOldAccountNumber() {
    return oldAccountNumber;
  }

  public void setOldAccountNumber(String oldAccountNumber) {
    this.oldAccountNumber = oldAccountNumber;
  }

  public String getSecurityNo() {
    return securityNo;
  }

  public void setSecurityNo(String securityNo) {
    this.securityNo = securityNo;
  }

  public void setIssueKind(String issueKind) {
    this.issueKind = issueKind;
  }

  public String getIssueKind() {
    return issueKind;
  }

  public void setOpenBranchName(String openBranchName) {
    this.openBranchName = openBranchName;
  }

  public String getOpenBranchName() {
    return openBranchName;
  }

  public void setOpenBranchTelNo(String openBranchTelNo) {
    this.openBranchTelNo = openBranchTelNo;
  }

  public String getOpenBranchTelNo() {
    return openBranchTelNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public String getIdNo() {
    return idNo;
  }

  public BigDecimal getBeforeInterestRate() {
    return beforeInterestRate;
  }

  public void setBeforeInterestRate(BigDecimal beforeInterestRate) {
    this.beforeInterestRate = beforeInterestRate;
  }

  public void setTrxNo(String trxNo) {
    this.trxNo = trxNo;
  }

  public String getTrxNo() {
    return trxNo;
  }

  public void setPassbookNo(String passbookNo) {
    this.passbookNo = passbookNo;
  }

  public String getPassbookNo() {
    return passbookNo;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setSecondaryAccount(String secondaryAccount) {
    this.secondaryAccount = secondaryAccount;
  }

  public String getSecondaryAccount() {
    return secondaryAccount;
  }

  public void setEndLine(int endLine) {
    this.endLine = endLine;
  }

  public int getEndLine() {
    return endLine;
  }

  public void setIntCalcStrtdate(String intCalcStrtdate) {
    this.intCalcStrtdate = intCalcStrtdate;
  }

  public String getIntCalcStrtdate() {
    return intCalcStrtdate;
  }

  public void setPbKind(String pbKind) {
    this.pbKind = pbKind;
  }

  public String getPbKind() {
    return pbKind;
  }

  public String getCalculationFee() {
    return calculationFee;
  }

  public void setCalculationFee(String calculationFee) {
    this.calculationFee = calculationFee;
  }

  public void setInclusionFee(String inclusionFee) {
    this.inclusionFee = inclusionFee;
  }

  public String getInclusionFee() {
    return inclusionFee;
  }

  public String getPbIssueKind() {
    return pbIssueKind;
  }

  public void setPbIssueKind(String pbIssueKind) {
    this.pbIssueKind = pbIssueKind;
  }

  public int getPbIssueCount() {
    return pbIssueCount;
  }

  public void setPbIssueCount(int pbIssueCount) {
    this.pbIssueCount = pbIssueCount;
  }

  public String getPbMsVersionPb() {
    return pbMsVersionPb;
  }

  public void setPbMsVersionPb(String pbMsVersionPb) {
    this.pbMsVersionPb = pbMsVersionPb;
  }

  public String getPbMsVersionDb() {
    return pbMsVersionDb;
  }

  public void setPbMsVersionDb(String pbMsVersionDb) {
    this.pbMsVersionDb = pbMsVersionDb;
  }

  public int getPbLinePerPage() {
    return pbLinePerPage;
  }

  public void setPbLinePerPage(int pbLinePerPage) {
    this.pbLinePerPage = pbLinePerPage;
  }

  public int getPbSkipPerPage() {
    return pbSkipPerPage;
  }

  public void setPbSkipPerPage(int pbSkipPerPage) {
    this.pbSkipPerPage = pbSkipPerPage;
  }

  public String getCommonCode() {
    return commonCode;
  }

  public void setCommonCode(String commonCode) {
    this.commonCode = commonCode;
  }

  public String getCommonName() {
    return commonName;
  }

  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }

  public String getCommonType() {
    return commonType;
  }

  public void setCommonType(String commonType) {
    this.commonType = commonType;
  }

  public String getCommonValue() {
    return commonValue;
  }

  public void setCommonValue(String commonValue) {
    this.commonValue = commonValue;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public String getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  private java.util.List pageList;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
    result = prime * result + ((bankCode == null) ? 0 : bankCode.hashCode());
    result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
    result = prime * result + ((accountGroup == null) ? 0 : accountGroup.hashCode());
    result = prime * result + ((accountGroupKind == null) ? 0 : accountGroupKind.hashCode());
    result = prime * result + ((CIFName == null) ? 0 : CIFName.hashCode());
    result = prime * result + ((CIFNo == null) ? 0 : CIFNo.hashCode());
    result = prime * result + ((openDate == null) ? 0 : openDate.hashCode());
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + ((grossBalance == null) ? 0 : grossBalance.hashCode());
    result = prime * result + ((holdBalance == null) ? 0 : holdBalance.hashCode());
    result = prime * result + ((lastCreditDate == null) ? 0 : lastCreditDate.hashCode());
    result = prime * result + ((lastCreditAmount == null) ? 0 : lastCreditAmount.hashCode());
    result = prime * result + ((lastDebitDate == null) ? 0 : lastDebitDate.hashCode());
    result = prime * result + ((lastDebitAmount == null) ? 0 : lastDebitAmount.hashCode());
    result = prime * result + ((collectedBalance == null) ? 0 : collectedBalance.hashCode());
    result = prime * result + ((memoBalance == null) ? 0 : memoBalance.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((uncollectedBalance == null) ? 0 : uncollectedBalance.hashCode());
    result = prime * result + ((feeCurrency == null) ? 0 : feeCurrency.hashCode());
    result = prime * result + ((feeAmount == null) ? 0 : feeAmount.hashCode());
    result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
    result = prime * result + ((glCode == null) ? 0 : glCode.hashCode());
    result = prime * result + ((expireDate == null) ? 0 : expireDate.hashCode());
    result = prime * result + ((interestIndex == null) ? 0 : interestIndex.hashCode());
    result = prime * result + ((interestRate == null) ? 0 : interestRate.hashCode());
    result = prime * result + ((tempBalance == null) ? 0 : tempBalance.hashCode());
    result = prime * result + lastPbLine;
    result = prime * result + pageNumber;
    result = prime * result + linePerPage;
    result = prime * result + ((passbookBalance == null) ? 0 : passbookBalance.hashCode());
    result = prime * result + ((accountPostingListDTO == null) ? 0 : accountPostingListDTO.hashCode());
    result = prime * result + ((stateVoucherImageCDTO == null) ? 0 : stateVoucherImageCDTO.hashCode());
    result = prime * result + sequenceNumber;
    result = prime * result + ((oldAccountNumber == null) ? 0 : oldAccountNumber.hashCode());
    result = prime * result + ((securityNo == null) ? 0 : securityNo.hashCode());
    result = prime * result + ((issueKind == null) ? 0 : issueKind.hashCode());
    result = prime * result + ((openBranchName == null) ? 0 : openBranchName.hashCode());
    result = prime * result + ((openBranchTelNo == null) ? 0 : openBranchTelNo.hashCode());
    result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
    result = prime * result + ((beforeInterestRate == null) ? 0 : beforeInterestRate.hashCode());
    result = prime * result + ((trxNo == null) ? 0 : trxNo.hashCode());
    result = prime * result + ((passbookNo == null) ? 0 : passbookNo.hashCode());
    result = prime * result + count;
    result = prime * result + ((secondaryAccount == null) ? 0 : secondaryAccount.hashCode());
    result = prime * result + endLine;
    result = prime * result + ((intCalcStrtdate == null) ? 0 : intCalcStrtdate.hashCode());
    result = prime * result + ((pbKind == null) ? 0 : pbKind.hashCode());
    result = prime * result + ((checkNumber == null) ? 0 : checkNumber.hashCode());
    result = prime * result + lastPbLine1;
    result = prime * result + ((inclusionFee == null) ? 0 : inclusionFee.hashCode());
    result = prime * result + ((calculationFee == null) ? 0 : calculationFee.hashCode());
    result = prime * result + ((pbIssueKind == null) ? 0 : pbIssueKind.hashCode());
    result = prime * result + pbIssueCount;
    result = prime * result + ((pbMsVersionPb == null) ? 0 : pbMsVersionPb.hashCode());
    result = prime * result + ((pbMsVersionDb == null) ? 0 : pbMsVersionDb.hashCode());
    result = prime * result + pbLinePerPage;
    result = prime * result + pbSkipPerPage;
    result = prime * result + ((renewDate == null) ? 0 : renewDate.hashCode());
    result = prime * result + ((pageList == null) ? 0 : pageList.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EPLcommonCDTO other = (EPLcommonCDTO) obj;
    if (accountNumber == null) {
      if (other.accountNumber != null)
        return false;
    } else if (!accountNumber.equals(other.accountNumber))
      return false;
    if (bankCode == null) {
      if (other.bankCode != null)
        return false;
    } else if (!bankCode.equals(other.bankCode))
      return false;
    if (branchCode == null) {
      if (other.branchCode != null)
        return false;
    } else if (!branchCode.equals(other.branchCode))
      return false;
    if (accountGroup == null) {
      if (other.accountGroup != null)
        return false;
    } else if (!accountGroup.equals(other.accountGroup))
      return false;
    if (accountGroupKind == null) {
      if (other.accountGroupKind != null)
        return false;
    } else if (!accountGroupKind.equals(other.accountGroupKind))
      return false;
    if (CIFName == null) {
      if (other.CIFName != null)
        return false;
    } else if (!CIFName.equals(other.CIFName))
      return false;
    if (CIFNo == null) {
      if (other.CIFNo != null)
        return false;
    } else if (!CIFNo.equals(other.CIFNo))
      return false;
    if (openDate == null) {
      if (other.openDate != null)
        return false;
    } else if (!openDate.equals(other.openDate))
      return false;
    if (currency == null) {
      if (other.currency != null)
        return false;
    } else if (!currency.equals(other.currency))
      return false;
    if (grossBalance == null) {
      if (other.grossBalance != null)
        return false;
    } else if (!grossBalance.equals(other.grossBalance))
      return false;
    if (holdBalance == null) {
      if (other.holdBalance != null)
        return false;
    } else if (!holdBalance.equals(other.holdBalance))
      return false;
    if (lastCreditDate == null) {
      if (other.lastCreditDate != null)
        return false;
    } else if (!lastCreditDate.equals(other.lastCreditDate))
      return false;
    if (lastCreditAmount == null) {
      if (other.lastCreditAmount != null)
        return false;
    } else if (!lastCreditAmount.equals(other.lastCreditAmount))
      return false;
    if (lastDebitDate == null) {
      if (other.lastDebitDate != null)
        return false;
    } else if (!lastDebitDate.equals(other.lastDebitDate))
      return false;
    if (lastDebitAmount == null) {
      if (other.lastDebitAmount != null)
        return false;
    } else if (!lastDebitAmount.equals(other.lastDebitAmount))
      return false;
    if (collectedBalance == null) {
      if (other.collectedBalance != null)
        return false;
    } else if (!collectedBalance.equals(other.collectedBalance))
      return false;
    if (memoBalance == null) {
      if (other.memoBalance != null)
        return false;
    } else if (!memoBalance.equals(other.memoBalance))
      return false;
    if (status == null) {
      if (other.status != null)
        return false;
    } else if (!status.equals(other.status))
      return false;
    if (uncollectedBalance == null) {
      if (other.uncollectedBalance != null)
        return false;
    } else if (!uncollectedBalance.equals(other.uncollectedBalance))
      return false;
    if (feeCurrency == null) {
      if (other.feeCurrency != null)
        return false;
    } else if (!feeCurrency.equals(other.feeCurrency))
      return false;
    if (feeAmount == null) {
      if (other.feeAmount != null)
        return false;
    } else if (!feeAmount.equals(other.feeAmount))
      return false;
    if (remarks == null) {
      if (other.remarks != null)
        return false;
    } else if (!remarks.equals(other.remarks))
      return false;
    if (glCode == null) {
      if (other.glCode != null)
        return false;
    } else if (!glCode.equals(other.glCode))
      return false;
    if (expireDate == null) {
      if (other.expireDate != null)
        return false;
    } else if (!expireDate.equals(other.expireDate))
      return false;
    if (interestIndex == null) {
      if (other.interestIndex != null)
        return false;
    } else if (!interestIndex.equals(other.interestIndex))
      return false;
    if (interestRate == null) {
      if (other.interestRate != null)
        return false;
    } else if (!interestRate.equals(other.interestRate))
      return false;
    if (tempBalance == null) {
      if (other.tempBalance != null)
        return false;
    } else if (!tempBalance.equals(other.tempBalance))
      return false;
    if (lastPbLine != other.lastPbLine)
      return false;
    if (pageNumber != other.pageNumber)
      return false;
    if (linePerPage != other.linePerPage)
      return false;
    if (passbookBalance == null) {
      if (other.passbookBalance != null)
        return false;
    } else if (!passbookBalance.equals(other.passbookBalance))
      return false;
    if (accountPostingListDTO == null) {
      if (other.accountPostingListDTO != null)
        return false;
    } else if (!accountPostingListDTO.equals(other.accountPostingListDTO))
      return false;
    if (stateVoucherImageCDTO == null) {
      if (other.stateVoucherImageCDTO != null)
        return false;
    } else if (!stateVoucherImageCDTO.equals(other.stateVoucherImageCDTO))
      return false;
    if (sequenceNumber != other.sequenceNumber)
      return false;
    if (oldAccountNumber == null) {
      if (other.oldAccountNumber != null)
        return false;
    } else if (!oldAccountNumber.equals(other.oldAccountNumber))
      return false;
    if (securityNo == null) {
      if (other.securityNo != null)
        return false;
    } else if (!securityNo.equals(other.securityNo))
      return false;
    if (issueKind == null) {
      if (other.issueKind != null)
        return false;
    } else if (!issueKind.equals(other.issueKind))
      return false;
    if (openBranchName == null) {
      if (other.openBranchName != null)
        return false;
    } else if (!openBranchName.equals(other.openBranchName))
      return false;
    if (openBranchTelNo == null) {
      if (other.openBranchTelNo != null)
        return false;
    } else if (!openBranchTelNo.equals(other.openBranchTelNo))
      return false;
    if (idNo == null) {
      if (other.idNo != null)
        return false;
    } else if (!idNo.equals(other.idNo))
      return false;
    if (beforeInterestRate == null) {
      if (other.beforeInterestRate != null)
        return false;
    } else if (!beforeInterestRate.equals(other.beforeInterestRate))
      return false;
    if (trxNo == null) {
      if (other.trxNo != null)
        return false;
    } else if (!trxNo.equals(other.trxNo))
      return false;
    if (passbookNo == null) {
      if (other.passbookNo != null)
        return false;
    } else if (!passbookNo.equals(other.passbookNo))
      return false;
    if (count != other.count)
      return false;
    if (secondaryAccount == null) {
      if (other.secondaryAccount != null)
        return false;
    } else if (!secondaryAccount.equals(other.secondaryAccount))
      return false;
    if (endLine != other.endLine)
      return false;
    if (intCalcStrtdate == null) {
      if (other.intCalcStrtdate != null)
        return false;
    } else if (!intCalcStrtdate.equals(other.intCalcStrtdate))
      return false;
    if (pbKind == null) {
      if (other.pbKind != null)
        return false;
    } else if (!pbKind.equals(other.pbKind))
      return false;
    if (checkNumber == null) {
      if (other.checkNumber != null)
        return false;
    } else if (!checkNumber.equals(other.checkNumber))
      return false;
    if (lastPbLine1 != other.lastPbLine1)
      return false;
    if (inclusionFee == null) {
      if (other.inclusionFee != null)
        return false;
    } else if (!inclusionFee.equals(other.inclusionFee))
      return false;
    if (calculationFee == null) {
      if (other.calculationFee != null)
        return false;
    } else if (!calculationFee.equals(other.calculationFee))
      return false;
    if (pbIssueKind == null) {
      if (other.pbIssueKind != null)
        return false;
    } else if (!pbIssueKind.equals(other.pbIssueKind))
      return false;
    if (pbIssueCount != other.pbIssueCount)
      return false;
    if (pbMsVersionPb == null) {
      if (other.pbMsVersionPb != null)
        return false;
    } else if (!pbMsVersionPb.equals(other.pbMsVersionPb))
      return false;
    if (pbMsVersionDb == null) {
      if (other.pbMsVersionDb != null)
        return false;
    } else if (!pbMsVersionDb.equals(other.pbMsVersionDb))
      return false;
    if (pbLinePerPage != other.pbLinePerPage)
      return false;
    if (pbSkipPerPage != other.pbSkipPerPage)
      return false;
    if (renewDate == null) {
      if (other.renewDate != null)
        return false;
    } else if (!renewDate.equals(other.renewDate))
      return false;
    if (pageList == null) {
      if (other.pageList != null)
        return false;
    } else if (!pageList.equals(other.pageList))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "EPLcommonCDTO{" +
        "commonCode='" + commonCode + '\'' +
        ", commonName='" + commonName + '\'' +
        ", commonType='" + commonType + '\'' +
        ", commonValue='" + commonValue + '\'' +
        ", description='" + description + '\'' +
        ", isActive=" + isActive +
        ", effectiveDate='" + effectiveDate + '\'' +
        ", expiryDate='" + expiryDate + '\'' +
        '}';
  }
}
