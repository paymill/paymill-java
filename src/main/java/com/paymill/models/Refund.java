package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class Refund {

  public enum Status {
    OPEN, REFUNDED, FAILED
  }

  private String      id;
  private Integer     amount;
  @JsonIgnore
  private Status      status;
  private String      description;
  private Transaction transaction;
  private Date        createdAt;
  private Date        updatedAt;

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId( String id ) {
    this.id = id;
  }

  /**
   * @return the amount
   */
  public Integer getAmount() {
    return amount;
  }

  /**
   * @param amount
   *          the amount to set
   */
  public void setAmount( Integer amount ) {
    this.amount = amount;
  }

  /**
   * @return the status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus( Status status ) {
    this.status = status;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription( String description ) {
    this.description = description;
  }

  /**
   * @return the createdAt
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * @param createdAt
   *          the createdAt to set
   */
  public void setCreatedAt( Date createdAt ) {
    this.createdAt = createdAt;
  }

  /**
   * @return the updatedAt
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * @param updatedAt
   *          the updatedAt to set
   */
  public void setUpdatedAt( Date updatedAt ) {
    this.updatedAt = updatedAt;
  }

  /**
   * @return the transaction
   */
  public Transaction getTransaction() {
    return transaction;
  }

  /**
   * @param transaction
   *          the transaction to set
   */
  public void setTransaction( Transaction transaction ) {
    this.transaction = transaction;
  }
}
