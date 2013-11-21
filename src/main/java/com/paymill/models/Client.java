package com.paymill.models;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The clients object is used to edit, delete, update clients as well as to permit refunds, subscriptions, insert credit card
 * details for a client, edit client details and of course make transactions. Clients can be created individually by you or they
 * will be automatically generated with the transaction if there is no client ID transmitted.
 * @author Vassil Nikolov
 */
@Data
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

  public Client( String id ) {
    this.id = id;
  }

  public void setCreatedAt( Date createdAt ) {
    this.createdAt = new Date( createdAt.getTime() * 1000 );
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

    public Client.Filter byDescription( String description ) {
      this.description = description;
      return this;
    }

    public Client.Filter byEmail( String email ) {
      this.email = email;
      return this;
    }

    public Client.Filter byPayment( String paymentId ) {
      this.paymentId = paymentId;
      return this;
    }

    public Client.Filter bySubscriptionId( String subscriptionId ) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Client.Filter byOfferId( String offerId ) {
      this.offerId = offerId;
      return this;
    }

    public Client.Filter byCreatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }

    public Client.Filter byUpdatedAt( Date startUpdatedAt, Date endUpdatedAt ) {
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
