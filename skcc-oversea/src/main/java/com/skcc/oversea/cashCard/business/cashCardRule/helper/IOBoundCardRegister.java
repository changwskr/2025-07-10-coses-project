package com.skcc.oversea.cashCard.business.cashCardRule.helper;

/**
 * IOBoundCardRegister Interface
 * 
 * Interface for card registration operations
 */
public interface IOBoundCardRegister {

  /**
   * Execute card registration with file
   * 
   * @param fname file name containing card data
   */
  void execute(String fname);

  /**
   * Execute card registration with individual parameters
   * 
   * @param acctno account number
   * @param name   customer name
   * @param branch branch code
   * @param cardno card number
   */
  void execute(String acctno, String name, String branch, String cardno);
}
