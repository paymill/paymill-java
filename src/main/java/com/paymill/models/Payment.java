package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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

  // NOTE[VNi]: Paymill supports two cards, namely direct debit and credit card

  // Common attributes:
  private String       id;

  private Payment.Type type;

  @JsonProperty( "created_at" )
  private Date         createdAt;

  @JsonProperty( "updated_at" )
  private Date         updatedAt;

  @JsonProperty( "app_id" )
  private String             appId;

  // Direct debit attributes
  private String       code;

  private String       account;

  private String       holder;

  // Credit card attributes
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
    public static Type create( String value ) {
      for( Type type : Type.values() ) {
        if( type.getValue().equals( value ) ) {
          return type;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Payment.Type" );
    }

  }

}
