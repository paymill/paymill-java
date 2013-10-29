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
public class Preauthorization {
  private String                  id;

  private Integer                 amount;

  private String                  description;

  private Preauthorization.Status status;

  private Boolean                 livemode;

  private Payment                 payment;

  private Client                  client;

  @JsonProperty( "created_at" )
  private Date                    createdAt;

  @JsonProperty( "updated_at" )
  private Date                    updatedAt;

  @JsonProperty( "app_id" )
  private String                  appId;

  @JsonProperty( "origin_amount" )
  private Integer                 originAmount;

  private String                  currency;

  private List<Refund>            refunds;

  public enum Status {
    PREAUTH("preauth"), OPEN("open"), CLOSED("closed"), DELETED("deleted"), FAILED("failed"), PENDING("pending");

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
      throw new IllegalArgumentException( "Invalid value for Preauthorization.Status" );
    }

  }

}
