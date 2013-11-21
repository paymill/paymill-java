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

  public Transaction() {
    super();
  }

  public Transaction( String id ) {
    this.id = id;
  }

  private String             id;

  private Integer            amount;

  @JsonProperty( "origin_amount" )
  private Integer            originAmount;

  private String             currency;

  private Transaction.Status status;

  @Updateable( "description" )
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

  public void setCreatedAt( Date createdAt ) {
    this.createdAt = new Date( createdAt.getTime() * 1000 );
  }

  public static Transaction.Filter createFilter() {
    return new Transaction.Filter();
  }

  public static Transaction.Order createOrder() {
    return new Transaction.Order();
  }

  public final static class Filter {

    @SnakeCase( "client_id" )
    private String clientId;

    @SnakeCase( "payment_id" )
    private String paymentId;

    @SnakeCase( "amount" )
    private String amount;

    @SnakeCase( "description" )
    private String description;

    @SnakeCase( "created_at" )
    private String createdAt;

    @SnakeCase( "updated_at" )
    private String updatedAt;

    @SnakeCase( "status" )
    private String status;

    private Filter() {
      super();
    }

    public Transaction.Filter byClientId( String clientId ) {
      this.clientId = clientId;
      return this;
    }

    public Transaction.Filter byPaymentId( String paymentId ) {
      this.paymentId = paymentId;
      return this;
    }

    public Transaction.Filter byAmount( int amount ) {
      this.amount = String.valueOf( amount );
      return this;
    }

    public Transaction.Filter byAmountGreaterThan( int amount ) {
      this.amount = ">" + String.valueOf( amount );
      return this;
    }

    public Transaction.Filter byAmountLessThan( int amount ) {
      this.amount = "<" + String.valueOf( amount );
      return this;
    }

    public Transaction.Filter byDescription( String description ) {
      this.description = description;
      return this;
    }

    public Transaction.Filter byCreatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }

    public Transaction.Filter byUpdatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.updatedAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }

    public Transaction.Filter byStatus( Transaction.Status status ) {
      this.status = status.getValue();
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

    public Transaction.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Transaction.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Transaction.Order byCreatedAt() {
      this.createdAt = true;
      return this;
    }

  }

  public enum Status {

    OPEN("open"),

    PENDING("pending"),

    CLOSED("closed"),

    FAILED("failed"),

    PARTIAL_REFUNDED("partial_refunded"),

    REFUNDED("refunded"),

    PREAUTH("preauth");

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
