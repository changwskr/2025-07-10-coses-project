package com.ims.oversea.framework.transaction.bean;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TransactionInfoBIZTCF {

  private String transaction_no;
  private String system_name;
  private String operation_class_name;
  private String operation_class_method_name;
  private String request_name;

  public String getOperation_class_method_name() {
    return operation_class_method_name;
  }
  public String getOperation_class_name() {
    return operation_class_name;
  }
  public String getSystem_name() {
    return system_name;
  }
  public String getTransaction_no() {
    return transaction_no;
  }
  public void setTransaction_no(String transaction_no) {
    this.transaction_no = transaction_no;
  }
  public void setSystem_name(String system_name) {
    this.system_name = system_name;
  }
  public void setOperation_class_name(String operation_class_name) {
    this.operation_class_name = operation_class_name;
  }
  public void setOperation_class_method_name(String operation_class_method_name) {
    this.operation_class_method_name = operation_class_method_name;
  }
  public String getRequest_name() {
    return request_name;
  }
  public void setRequest_name(String request_name) {
    this.request_name = request_name;
  }

}