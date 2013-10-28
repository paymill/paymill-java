package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.paymill.models.Transaction.Status;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Payment {
  // NOTE[VNi]: In some cases API returns only the Id of the payment.
  // Therefore we need constructor with String parameter.

  public Payment() {
    super();
  }

  public Payment( String id ) {
    this.id = id;
  }

  private String       id;

  private Payment.Type type;

  private String       client;

  @JsonProperty( "card_type" )
  private String       cardType;

  private String       country;

  @JsonProperty( "expire_month" )
  private Integer      expireMonth;

  @JsonProperty( "expire_year" )
  private Integer      expireYear;

  @JsonProperty( "card_holder" )
  private String       cardHolder;

  private String       last4;

  private String       code;

  private String       holder;

  private String       account;

  @JsonProperty( "created_at" )
  private Date         createdAt;

  @JsonProperty( "updated_at" )
  private Date         updatedAt;

  public enum Type {
    CREDITCARD("creditcard"), DEBIT("debit");

    private String value;

    private Type( String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Status create( String value ) {
      for( Status status : Status.values() ) {
        if( status.getValue().equals( value ) ) {
          return status;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Transaction.Status" );
    }

  }

}
