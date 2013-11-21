package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Subscription {

  public Subscription() {
    super();
  }

  public Subscription( String id ) {
    this.id = id;
  }

  private String  id;

  private Offer   offer;

  private Boolean livemode;

  @Updateable( "cancel_at_period_end" )
  @JsonProperty( "cancel_at_period_end" )
  private Boolean cancelAtPeriodEnd;

  @JsonProperty( "trial_start" )
  private Date    trialStart;

  @JsonProperty( "trial_end" )
  private Date    trialEnd;

  @JsonProperty( "next_capture_at" )
  private Date    nextCaptureAt;

  @JsonProperty( "created_at" )
  private Date    createdAt;

  @JsonProperty( "updated_at" )
  private Date    updatedAt;

  @JsonProperty( "canceled_at" )
  private Date    canceledAt;

  @Updateable( "payment" )
  private Payment payment;

  private Client  client;

  @JsonProperty( "app_id" )
  private String  appId;

  public void setCreatedAt( Date createdAt ) {
    this.createdAt = new Date( createdAt.getTime() * 1000 );
  }

  public static Subscription.Filter createFilter() {
    return new Subscription.Filter();
  }

  public static Subscription.Order createOrder() {
    return new Subscription.Order();
  }

  public final static class Filter {

    @SnakeCase( "offer_id" )
    private String offerId;

    @SnakeCase( "created_at" )
    private String createdAt;

    private Filter() {
      super();
    }

    public Subscription.Filter byOfferId( String offerId ) {
      this.offerId = offerId;
      return this;
    }

    public Subscription.Filter byCreatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }

  }

  public final static class Order {

    @SnakeCase( "offer" )
    private boolean offer;

    @SnakeCase( "canceled_at" )
    private boolean canceledAt;

    @SnakeCase( "created_at" )
    private boolean createdAt;

    @SnakeCase( value = "asc", order = true )
    private boolean asc;

    @SnakeCase( value = "desc", order = true )
    private boolean desc;

    private Order() {
      super();
    }

    public Subscription.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Subscription.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Subscription.Order byOffer() {
      this.offer = true;
      this.createdAt = false;
      this.canceledAt = false;
      return this;
    }

    public Subscription.Order byCanceledAt() {
      this.offer = false;
      this.createdAt = false;
      this.canceledAt = true;
      return this;
    }

    public Subscription.Order byCreatedAt() {
      this.offer = false;
      this.createdAt = true;
      this.canceledAt = false;
      return this;
    }

  }

}
