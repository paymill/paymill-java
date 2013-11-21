package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Preauthorization {

  private String                  id;

  private String                  amount;

  private String                  currency;

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

  public Preauthorization() {
    super();
  }

  public Preauthorization( String id ) {
    this.id = id;
  }

  public void setCreatedAt( Date createdAt ) {
    this.createdAt = new Date( createdAt.getTime() * 1000 );
  }

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

  public static Preauthorization.Filter createFilter() {
    return new Preauthorization.Filter();
  }

  public static Preauthorization.Order createOrder() {
    return new Preauthorization.Order();
  }

  public final static class Filter {

    @SnakeCase( "client_id" )
    private String clientId;

    @SnakeCase( "payment_id" )
    private String paymentId;

    @SnakeCase( "amount" )
    private String amount;

    @SnakeCase( "created_at" )
    private String createdAt;

    private Filter() {
      super();
    }

    public Preauthorization.Filter byClientId( String clientId ) {
      this.clientId = clientId;
      return this;
    }

    public Preauthorization.Filter byPaymentId( String paymentId ) {
      this.paymentId = paymentId;
      return this;
    }

    public Preauthorization.Filter byAmount( int amount ) {
      this.amount = String.valueOf( amount );
      return this;
    }

    public Preauthorization.Filter byAmountGreaterThan( int amount ) {
      this.amount = ">" + String.valueOf( amount );
      return this;
    }

    public Preauthorization.Filter byAmountLessThan( int amount ) {
      this.amount = "<" + String.valueOf( amount );
      return this;
    }

    public Preauthorization.Filter byCreatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }
  }

  public final static class Order {

    @SnakeCase( "created_at" )
    private boolean createdAt;

    @SnakeCase( value = "asc", order = true )
    private boolean asc;

    @SnakeCase( value = "desc", order = true )
    private boolean desc;

    private Order() {
      super();
    }

    public Preauthorization.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Preauthorization.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Preauthorization.Order byCreatedAt() {
      this.createdAt = true;
      return this;
    }

  }

}
