package com.paymill.models;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Transaction {

  private String             id;

  private Integer            amount;

  @JsonProperty( "origin_amount" )
  private Integer            originAmount;

  private String             currency;

  private Transaction.Status status;

  @Updateable
  private String             description;

  private Boolean            livemode;

  private List<Refund>       refunds;

  private Payment            payment;

  private Client             client;

  private Preauthorization   preauthorization;

  @JsonProperty( "created_at" )
  private Date               createdAt;

  @JsonProperty( "updated_at" )
  private Date               updatedAt;

  @JsonProperty( "response_code" )
  private String             responseCode;

  @JsonProperty( "short_id" )
  private String             shortId;

  @JsonProperty( "is_fraud" )
  private Boolean            fraud;

  private List<Fee>          fees;

  @JsonProperty( "app_id" )
  private String             appId;

  public enum Status {
    PARTIAL_REFUNDED("partial refunded"), REFUNDED("refunded"), CLOSED("closed"), FAILED("failed"), PENDING("pending");

    private String value;

    private Status( String value ) {
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
