package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public final class Fee {

  private Fee.Type type;

  private String   application;

  private String   payment;

  private Integer  amount;

  private String   currency;

  @JsonProperty( "billed_at" )
  private Date     billedAt;

  /**
   * Returns the fee type.
   * @return {@link Fee.Type}
   */
  public Fee.Type getType() {
    return this.type;
  }

  /**
   * Sets the fee type.
   * @param type
   *          {@link Fee.Type}
   */
  public void setType( final Fee.Type type ) {
    this.type = type;
  }

  /**
   * Returns unique identifier of the application which charges the fee.
   * @return {@link String}
   */
  public String getApplication() {
    return this.application;
  }

  /**
   * Sets the unique identifier of the application which charges the fee.
   * @param application
   *          {@link String}
   */
  public void setApplication( final String application ) {
    this.application = application;
  }

  /**
   * Returns unique identifier of the payment from which the fee will be charged.
   * @return {@link String}
   */
  public String getPayment() {
    return this.payment;
  }

  /**
   * Sets the unique identifier of the payment from which the fee will be charged.
   * @param payment
   *          {@link String}
   */
  public void setPayment( final String payment ) {
    this.payment = payment;
  }

  /**
   * Returns fee amount in the smallest currency unit e.g. “420” for $4.20.
   * @return {@link Integer}
   */
  public Integer getAmount() {
    return this.amount;
  }

  /**
   * Sets the fee amount in the smallest currency unit e.g. “420” for $4.20.
   * @param amount
   *          {@link Integer}
   */
  public void setAmount( final Integer amount ) {
    this.amount = amount;
  }

  /**
   * Returns ISO 4217 formatted currency code.
   * @return {@link String}
   */
  public String getCurrency() {
    return this.currency;
  }

  /**
   * Sets the ISO 4217 formatted currency code.
   * @param currency
   *          {@link String}
   */
  public void setCurrency( final String currency ) {
    this.currency = currency;
  }

  /**
   * Returns the billing date.
   * @return {@link Date}
   */
  public Date getBilledAt() {
    return this.billedAt;
  }

  /**
   * Sets the billing date.
   * @param billedAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setBilledAt( final Date billedAt ) {
    this.billedAt = billedAt;
  }

  /**
   * Sets the billing date.
   * @param seconds
   *          Billing date representation is seconds.
   */
  public void setBilledAt( final long seconds ) {
    this.billedAt = new Date( seconds * 1000 );
  }

  public enum Type {

    APPLICATION("application");

    private String value;

    private Type( final String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Type create( final String value ) {
      for( Type type : Type.values() ) {
        if( type.getValue().equals( value ) ) {
          return type;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Fee.Type" );
    }
  }

}
