package com.skcc.oversea.framework.transaction.dao;

import com.skcc.oversea.foundation.utility.StringUtils;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION_UP_DOWN")
public class TransactionUpDownEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "TRANSACTION_ID")
  private String transactionId;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "CREATE_DATE")
  private LocalDateTime createDate;

  // Constructors
  public TransactionUpDownEntity() {
  }

  public TransactionUpDownEntity(String transactionId, String status) {
    this.transactionId = transactionId;
    this.status = status;
    this.createDate = LocalDateTime.now();
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }
}
