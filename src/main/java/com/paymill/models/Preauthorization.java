package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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

  public Preauthorization( final String id ) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setId( final String id ) {
    this.id = id;
  }

  public String getAmount() {
    return this.amount;
  }

  public void setAmount( final String amount ) {
    this.amount = amount;
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency( final String currency ) {
    this.currency = currency;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription( final String description ) {
    this.description = description;
  }

  public Preauthorization.Status getStatus() {
    return this.status;
  }

  public void setStatus( final Preauthorization.Status status ) {
    this.status = status;
  }

  public Boolean getLivemode() {
    return this.livemode;
  }

  public void setLivemode( final Boolean livemode ) {
    this.livemode = livemode;
  }

  public Payment getPayment() {
    return this.payment;
  }

  public void setPayment( final Payment payment ) {
    this.payment = payment;
  }

  public Client getClient() {
    return this.client;
  }

  public void setClient( final Client client ) {
    this.client = client;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt( final Date updatedAt ) {
    this.updatedAt = new Date( updatedAt.getTime() * 1000 );
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

  public enum Status {
    PREAUTH("preauth"), OPEN("open"), CLOSED("closed"), DELETED("deleted"), FAILED("failed"), PENDING("pending");

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

    public Preauthorization.Filter byClientId( final String clientId ) {
      this.clientId = clientId;
      return this;
    }

    public Preauthorization.Filter byPaymentId( final String paymentId ) {
      this.paymentId = paymentId;
      return this;
    }

    public Preauthorization.Filter byAmount( final int amount ) {
      this.amount = String.valueOf( amount );
      return this;
    }

    public Preauthorization.Filter byAmountGreaterThan( final int amount ) {
      this.amount = ">" + String.valueOf( amount );
      return this;
    }

    public Preauthorization.Filter byAmountLessThan( final int amount ) {
      this.amount = "<" + String.valueOf( amount );
      return this;
    }

    public Preauthorization.Filter byCreatedAt( final Date startCreatedAt, final Date endCreatedAt ) {
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
