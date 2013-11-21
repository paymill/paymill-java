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

  public void setCreatedAt( Date createdAt ) {
    this.createdAt = new Date( createdAt.getTime() * 1000 );
  }

  public static Refund.Filter createFilter() {
    return new Refund.Filter();
  }

  public static Refund.Order createOrder() {
    return new Refund.Order();
  }

  public final static class Filter {

    @SnakeCase( "client_id" )
    private String clientId;

    @SnakeCase( "transaction_id" )
    private String transactionId;

    @SnakeCase( "amount" )
    private String amount;

    @SnakeCase( "created_at" )
    private String createdAt;


    private Filter() {
      super();
    }

    public Refund.Filter byClientId( String clientId ) {
      this.clientId = clientId;
      return this;
    }

    public Refund.Filter byTransactionId( String transactionId ) {
      this.transactionId = transactionId;
      return this;
    }

    public Refund.Filter byAmount( int amount ) {
      this.amount = String.valueOf( amount );
      return this;
    }

    public Refund.Filter byAmountGreaterThan( int amount ) {
      this.amount = ">" + String.valueOf( amount );
      return this;
    }

    public Refund.Filter byAmountLessThan( int amount ) {
      this.amount = "<" + String.valueOf( amount );
      return this;
    }

    public Refund.Filter byCreatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }

  }

  public final static class Order {

    @SnakeCase( "transaction" )
    private boolean transaction;

    @SnakeCase( "client" )
    private boolean client;

    @SnakeCase( "amount" )
    private boolean amount;

    @SnakeCase( "created_at" )
    private boolean createdAt;

    @SnakeCase( value = "asc", order = true )
    private boolean asc;

    @SnakeCase( value = "desc", order = true )
    private boolean desc;

    private Order() {
      super();
    }

    public Refund.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Refund.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Refund.Order byTransaction() {
      this.transaction = true;
      this.client = false;
      this.amount = false;
      this.createdAt = false;
      return this;
    }

    public Refund.Order byClient() {
      this.transaction = false;
      this.client = true;
      this.amount = false;
      this.createdAt = false;
      return this;
    }

    public Refund.Order byAmount() {
      this.transaction = false;
      this.client = false;
      this.amount = true;
      this.createdAt = false;
      return this;
    }

    public Refund.Order byCreatedAt() {
      this.transaction = false;
      this.client = false;
      this.amount = false;
      this.createdAt = true;
      return this;
    }

  }

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
