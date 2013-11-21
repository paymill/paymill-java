package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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

  public String getId() {
    return this.id;
  }

  public void setId( final String id ) {
    this.id = id;
  }

  public Transaction getTransaction() {
    return this.transaction;
  }

  public void setTransaction( final Transaction transaction ) {
    this.transaction = transaction;
  }

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount( final Integer amount ) {
    this.amount = amount;
  }

  public Refund.Status getStatus() {
    return this.status;
  }

  public void setStatus( final Refund.Status status ) {
    this.status = status;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription( final String description ) {
    this.description = description;
  }

  public Boolean getLivemode() {
    return this.livemode;
  }

  public void setLivemode( final Boolean livemode ) {
    this.livemode = livemode;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt( final Date updatedAt ) {
    this.updatedAt = new Date( updatedAt.getTime() * 1000 );
  }

  public Integer getResponseCode() {
    return this.responseCode;
  }

  public void setResponseCode( final Integer responseCode ) {
    this.responseCode = responseCode;
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId( final String appId ) {
    this.appId = appId;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt( final Date createdAt ) {
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

    public Refund.Filter byClientId( final String clientId ) {
      this.clientId = clientId;
      return this;
    }

    public Refund.Filter byTransactionId( final String transactionId ) {
      this.transactionId = transactionId;
      return this;
    }

    public Refund.Filter byAmount( final int amount ) {
      this.amount = String.valueOf( amount );
      return this;
    }

    public Refund.Filter byAmountGreaterThan( final int amount ) {
      this.amount = ">" + String.valueOf( amount );
      return this;
    }

    public Refund.Filter byAmountLessThan( final int amount ) {
      this.amount = "<" + String.valueOf( amount );
      return this;
    }

    public Refund.Filter byCreatedAt( final Date startCreatedAt, final Date endCreatedAt ) {
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

    private Status( final String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Status create( final String value ) {
      for( Status status : Status.values() ) {
        if( status.getValue().equals( value ) ) {
          return status;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Refund.Status" );
    }

  }

}
