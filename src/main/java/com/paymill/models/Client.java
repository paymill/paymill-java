package com.paymill.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The clients object is used to edit, delete, update clients as well as to permit refunds, subscriptions, insert credit card
 * details for a client, edit client details and of course make transactions. Clients can be created individually by you or they
 * will be automatically generated with the transaction if there is no client ID transmitted.
 * @author Vassil Nikolov
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Client {

  /**
   * @serial Unique identifier of this client.
   */
  private String             id;

  /**
   * Mail address of this client.
   */
  @Updateable( "email" )
  private String             email;

  @Updateable( "description" )
  private String             description;

  @JsonProperty( "created_at" )
  private Date               createdAt;

  @JsonProperty( "updated_at" )
  private Date               updatedAt;

  @JsonProperty( "payment" )
  private List<Payment>      payments;

  @JsonProperty( "subscription" )
  private List<Subscription> subscriptions;

  @JsonProperty( "app_id" )
  private String             appId;

  public Client() {
    super();
  }

  public Client( final String id ) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setId( final String id ) {
    this.id = id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail( final String email ) {
    this.email = email;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription( final String description ) {
    this.description = description;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  @JsonIgnore
  public void setCreatedAt( final Date createdAt ) {
    this.createdAt = createdAt;
  }

  public void setCreatedAt( final long seconds ) {
    this.createdAt = new Date( seconds * 1000 );
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  @JsonIgnore
  public void setUpdatedAt( final Date updatedAt ) {
    this.updatedAt = updatedAt;
  }

  public void setUpdatedAt( final long seconds ) {
    this.updatedAt = new Date( seconds * 1000 );
  }

  public List<Payment> getPayments() {
    return this.payments;
  }

  public void setPayments( final List<Payment> payments ) {
    this.payments = payments;
  }

  public List<Subscription> getSubscriptions() {
    return this.subscriptions;
  }

  public void setSubscriptions( final List<Subscription> subscriptions ) {
    this.subscriptions = subscriptions;
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId( final String appId ) {
    this.appId = appId;
  }

  public static Client.Filter createFilter() {
    return new Client.Filter();
  }

  public static Client.Order createOrder() {
    return new Client.Order();
  }

  public final static class Filter {

    @SnakeCase( "payment_id" )
    private String paymentId;

    @SnakeCase( "subscription_id" )
    private String subscriptionId;

    @SnakeCase( "offer_id" )
    private String offerId;

    @SnakeCase( "description" )
    private String description;

    @SnakeCase( "email" )
    private String email;

    @SnakeCase( "created_at" )
    private String createdAt;

    @SnakeCase( "updated_at" )
    private String updatedAt;

    private Filter() {
      super();
    }

    public Client.Filter byDescription( final String description ) {
      this.description = description;
      return this;
    }

    public Client.Filter byEmail( final String email ) {
      this.email = email;
      return this;
    }

    public Client.Filter byPayment( final String paymentId ) {
      this.paymentId = paymentId;
      return this;
    }

    public Client.Filter bySubscriptionId( final String subscriptionId ) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Client.Filter byOfferId( final String offerId ) {
      this.offerId = offerId;
      return this;
    }

    public Client.Filter byCreatedAt( final Date startCreatedAt, final Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }

    public Client.Filter byUpdatedAt( final Date startUpdatedAt, final Date endUpdatedAt ) {
      this.updatedAt = String.valueOf( startUpdatedAt.getTime() ) + "-" + String.valueOf( endUpdatedAt.getTime() );
      return this;
    }
  }

  public final static class Order {

    @SnakeCase( "email" )
    private boolean email;

    @SnakeCase( "created_at" )
    private boolean createdAt;

    @SnakeCase( "creditcard" )
    private boolean creditCard;

    @SnakeCase( value = "asc", order = true )
    private boolean asc;

    @SnakeCase( value = "desc", order = true )
    private boolean desc;

    private Order() {
      super();
    }

    public Client.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Client.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Client.Order byCreatedAt() {
      this.email = false;
      this.createdAt = true;
      this.creditCard = false;
      return this;
    }

    public Client.Order byCreditCard() {
      this.email = false;
      this.createdAt = false;
      this.creditCard = true;
      return this;
    }

    public Client.Order byEmail() {
      this.email = true;
      this.createdAt = false;
      this.creditCard = false;
      return this;
    }
  }

}
