package com.paymill.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public final class Fee {

  private Fee.Type type;
  private String   application;
  private String   payment;
  private Integer  amount;

  public Fee.Type getType() {
    return this.type;
  }

  public void setType( final Fee.Type type ) {
    this.type = type;
  }

  public String getApplication() {
    return this.application;
  }

  public void setApplication( final String application ) {
    this.application = application;
  }

  public String getPayment() {
    return this.payment;
  }

  public void setPayment( final String payment ) {
    this.payment = payment;
  }

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount( final Integer amount ) {
    this.amount = amount;
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
