package com.kdb.oversea.eplatonframework.transfer;

import java.math.BigDecimal;
import com.chb.coses.framework.constants.Constants;
import com.chb.coses.glPosting.transfer.*;
import com.chb.coses.framework.transfer.ListDTO;
import com.chb.coses.linkage.transfer.AccountPostingListDTO;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 *  2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 *                                                        @author : 장우승(WooSungJang)
 *                                                        @company: IMS SYSTEM
 *                                                        @email  : changwskr@yahoo.co.kr
 *                                                        @version 1.0
 *  =============================================================================
 */

public class EPLcommonCDTO extends com.chb.coses.framework.transfer.DTO
{
  private String accountNumber;
  private String bankCode;
  private String branchCode;
  private String accountGroup;
  private String accountGroupKind;
  private String CIFName;
  private String CIFNo;
  private String openDate;
  private String currency;
  private BigDecimal grossBalance=Constants.ZERO;
  private BigDecimal holdBalance=Constants.ZERO;
  private String lastCreditDate;
  private BigDecimal lastCreditAmount=Constants.ZERO;
  private String lastDebitDate;
  private BigDecimal lastDebitAmount=Constants.ZERO;
  private BigDecimal collectedBalance=Constants.ZERO;
  private BigDecimal memoBalance=Constants.ZERO;
  private String status;
  private BigDecimal uncollectedBalance=Constants.ZERO;
  private String feeCurrency;
  private BigDecimal feeAmount=Constants.ZERO;
  private String remarks;
  private String glCode;
  private String expireDate;
  private String interestIndex;
  private java.math.BigDecimal interestRate=Constants.ZERO;
  private BigDecimal tempBalance=Constants.ZERO;
  private int lastPbLine=0;
  private int pageNumber;
  private int linePerPage;
  private BigDecimal passbookBalance=Constants.ZERO;
  private AccountPostingListDTO accountPostingListDTO;
  private StateVoucherImageCDTO stateVoucherImageCDTO;
  private int sequenceNumber;
  private String oldAccountNumber;
  private String securityNo;
  private String issueKind;
  private String openBranchName;
  private String openBranchTelNo;
  private String idNo;
  private BigDecimal beforeInterestRate=Constants.ZERO;
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

  public EPLcommonCDTO()
  {
    accountNumber="*";
    bankCode="*";
    branchCode="*";
    accountGroup="*";
    accountGroupKind="*";
    CIFName="*";
    CIFNo="*";
    openDate="*";
    currency="*";
    grossBalance=Constants.ZERO;
    holdBalance=Constants.ZERO;
    lastCreditDate="*";
    lastCreditAmount=Constants.ZERO;
    lastDebitDate="*";
    lastDebitAmount=Constants.ZERO;
    collectedBalance=Constants.ZERO;
    memoBalance=Constants.ZERO;
    status="*";
    uncollectedBalance=Constants.ZERO;
    feeCurrency="*";
    feeAmount=Constants.ZERO;
    remarks="*";
    glCode="*";
    expireDate="*";
    interestIndex="*";
    java.math.BigDecimal interestRate=Constants.ZERO;
    tempBalance=Constants.ZERO;
    int lastPbLine=0;
    int pageNumber=0;
    int linePerPage=0;
    passbookBalance=Constants.ZERO;
    AccountPostingListDTO accountPostingListDTO;
    StateVoucherImageCDTO stateVoucherImageCDTO;
    int sequenceNumber=0;
    oldAccountNumber="*";
    securityNo="*";
    issueKind="*";
    openBranchName="*";
    openBranchTelNo="*";
    idNo="*";
    beforeInterestRate=Constants.ZERO;
    trxNo="*";
    passbookNo="*";
    int count=0;
    secondaryAccount="*";
    int endLine=0;
    intCalcStrtdate="*";
    pbKind="*";
    checkNumber="*";
    int lastPbLine1=0;
    inclusionFee="*";
    calculationFee="*";
    pbIssueKind="*";
    int pbIssueCount=0;
    pbMsVersionPb="*";
    pbMsVersionDb="*";
    int pbLinePerPage=0;
    int pbSkipPerPage=0;
    renewDate="*";

  }
  public String getCheckNumber()
  {
    return checkNumber;
  }
  public void setCheckNumber(String checkNumber)
  {
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
  public void setCIFName(String CIFName)
  {
    this.CIFName = CIFName;
  }
  public String getCIFName()
  {
    return CIFName;
  }
  public void setLastDebitDate(String lastDebitDate)
  {
    this.lastDebitDate = lastDebitDate;
  }
  public String getLastDebitDate()
  {
    return lastDebitDate;
  }
  public void setLastCreditDate(String lastCreditDate)
  {
    this.lastCreditDate = lastCreditDate;
  }
  public String getLastCreditDate()
  {
    return lastCreditDate;
  }
  public String getAccountGroupKind()
  {
    return accountGroupKind;
  }
  public void setAccountGroupKind(String accountGroupKind)
  {
    this.accountGroupKind = accountGroupKind;
  }
  public String getBranchCode()
  {
    return branchCode;
  }
  public void setBranchCode(String branchCode)
  {
    this.branchCode = branchCode;
  }
  public BigDecimal getFeeAmount()
  {
    return feeAmount;
  }
  public String getFeeCurrency()
  {
    return feeCurrency;
  }
  public void setFeeAmount(BigDecimal feeAmount)
  {
    this.feeAmount = feeAmount;
  }
  public void setFeeCurrency(String feeCurrency)
  {
    this.feeCurrency = feeCurrency;
  }
  public BigDecimal getLastCreditAmount()
  {
    return lastCreditAmount;
  }
  public void setLastCreditAmount(BigDecimal lastCreditAmount)
  {
    this.lastCreditAmount = lastCreditAmount;
  }
  public BigDecimal getLastDebitAmount()
  {
    return lastDebitAmount;
  }
  public void setLastDebitAmount(BigDecimal lastDebitAmount)
  {
    this.lastDebitAmount = lastDebitAmount;
  }
  public String getRemarks()
  {
    return remarks;
  }
  public void setRemarks(String remarks)
  {
    this.remarks = remarks;
  }
  public String getGlCode()
  {
    return glCode;
  }
  public void setGlCode(String glCode)
  {
    this.glCode = glCode;
  }
  public void setExpireDate(String expireDate)
  {
    this.expireDate = expireDate;
  }
  public String getExpireDate()
  {
    return expireDate;
  }
  public void setInterestIndex(String interestIndex)
  {
    this.interestIndex = interestIndex;
  }
  public String getInterestIndex()
  {
    return interestIndex;
  }
  public void setInterestRate(java.math.BigDecimal interestRate)
  {
    this.interestRate = interestRate;
  }
  public java.math.BigDecimal getInterestRate()
  {
    return interestRate;
  }
  public AccountPostingListDTO getAccountPostingListDTO()
  {
    return accountPostingListDTO;
  }
  public void setAccountPostingListDTO(AccountPostingListDTO accountPostingListDTO)
  {
    this.accountPostingListDTO = accountPostingListDTO;
  }
  public StateVoucherImageCDTO getStateVoucherImageCDTO()
  {
    return stateVoucherImageCDTO;
  }
  public void setStateVoucherImageCDTO(StateVoucherImageCDTO stateVoucherImageCDTO)
  {
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
  public void setSequenceNumber(int sequenceNumber)
  {
    this.sequenceNumber = sequenceNumber;
  }
  public int getSequenceNumber()
  {
    return sequenceNumber;
  }
  public String getOldAccountNumber()
  {
    return oldAccountNumber;
  }
  public void setOldAccountNumber(String oldAccountNumber)
  {
    this.oldAccountNumber = oldAccountNumber;
  }
  public String getSecurityNo()
  {
    return securityNo;
  }
  public void setSecurityNo(String securityNo)
  {
    this.securityNo = securityNo;
  }
  public void setIssueKind(String issueKind)
  {
    this.issueKind = issueKind;
  }
  public String getIssueKind()
  {
    return issueKind;
  }
  public void setOpenBranchName(String openBranchName)
  {
    this.openBranchName = openBranchName;
  }
  public String getOpenBranchName()
  {
    return openBranchName;
  }
  public void setOpenBranchTelNo(String openBranchTelNo)
  {
    this.openBranchTelNo = openBranchTelNo;
  }
  public String getOpenBranchTelNo()
  {
    return openBranchTelNo;
  }
  public void setIdNo(String idNo)
  {
    this.idNo = idNo;
  }
  public String getIdNo()
  {
    return idNo;
  }
  public BigDecimal getBeforeInterestRate()
  {
    return beforeInterestRate;
  }
  public void setBeforeInterestRate(BigDecimal beforeInterestRate)
  {
    this.beforeInterestRate = beforeInterestRate;
  }
  public void setTrxNo(String trxNo)
  {
    this.trxNo = trxNo;
  }
  public String getTrxNo()
  {
    return trxNo;
  }
  public void setPassbookNo(String passbookNo) {
    this.passbookNo = passbookNo;
  }
  public String getPassbookNo() {
    return passbookNo;
  }
  public void setCount(int count)
  {
    this.count = count;
  }
  public int getCount()
  {
    return count;
  }
  public void setSecondaryAccount(String secondaryAccount)
  {
    this.secondaryAccount = secondaryAccount;
  }
  public String getSecondaryAccount()
  {
    return secondaryAccount;
  }
  public void setEndLine(int endLine)
  {
    this.endLine = endLine;
  }
  public int getEndLine()
  {
    return endLine;
  }
  public void setIntCalcStrtdate(String intCalcStrtdate)
  {
    this.intCalcStrtdate = intCalcStrtdate;
  }
  public String getIntCalcStrtdate()
  {
    return intCalcStrtdate;
  }
  public void setPbKind(String pbKind)
  {
    this.pbKind = pbKind;
  }
  public String getPbKind()
  {
    return pbKind;
  }
  public String getCalculationFee()
  {
    return calculationFee;
  }
  public void setCalculationFee(String calculationFee)
  {
    this.calculationFee = calculationFee;
  }
  public void setInclusionFee(String inclusionFee)
  {
    this.inclusionFee = inclusionFee;
  }
  public String getInclusionFee()
  {
    return inclusionFee;
  }
  public String getPbIssueKind()
  {
    return pbIssueKind;
  }
  public void setPbIssueKind(String pbIssueKind)
  {
    this.pbIssueKind = pbIssueKind;
  }
  public int getPbIssueCount()
  {
    return pbIssueCount;
  }
  public void setPbIssueCount(int pbIssueCount)
  {
    this.pbIssueCount = pbIssueCount;
  }
  public String getPbMsVersionPb()
  {
    return pbMsVersionPb;
  }
  public void setPbMsVersionPb(String pbMsVersionPb)
  {
    this.pbMsVersionPb = pbMsVersionPb;
  }
  public String getPbMsVersionDb()
  {
    return pbMsVersionDb;
  }
  public void setPbMsVersionDb(String pbMsVersionDb)
  {
    this.pbMsVersionDb = pbMsVersionDb;
  }
  public int getPbLinePerPage()
  {
    return pbLinePerPage;
  }
  public void setPbLinePerPage(int pbLinePerPage)
  {
    this.pbLinePerPage = pbLinePerPage;
  }
  public int getPbSkipPerPage()
  {
    return pbSkipPerPage;
  }

  public void setPbSkipPerPage(int pbSkipPerPage)
  {
    this.pbSkipPerPage = pbSkipPerPage;
  }

  private java.util.List pageList;


}