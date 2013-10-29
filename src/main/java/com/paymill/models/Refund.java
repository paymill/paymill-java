package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Refund {

  private String        id;

  private Transaction   transaction;

  private Integer       amount;

  private Refund.Status status;

  private String        description;

  private Boolean       livemode;

  @JsonProperty( "created_at" )
  private Date          createdAt;

  @JsonProperty( "updated_at" )
  private Date          updatedAt;

  @JsonProperty( "response_code" )
  private Integer       responseCode;

  @JsonProperty( "app_id" )
  private String        appId;

  public enum Status {
    OPEN("open"), REFUNDED("refunded"), FAILED("failed");

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
      throw new IllegalArgumentException( "Invalid value for Refund.Status" );
    }

  }

}
