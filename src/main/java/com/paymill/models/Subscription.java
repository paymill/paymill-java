package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( ignoreUnknown = true )
public class Subscription {

  public Subscription() {
    super();
  }

  public Subscription( final String id ) {
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

  public String getId() {
    return this.id;
  }

  public void setId( final String id ) {
    this.id = id;
  }

  public Offer getOffer() {
    return this.offer;
  }

  public void setOffer( final Offer offer ) {
    this.offer = offer;
  }

  public Boolean getLivemode() {
    return this.livemode;
  }

  public void setLivemode( final Boolean livemode ) {
    this.livemode = livemode;
  }

  public Boolean getCancelAtPeriodEnd() {
    return this.cancelAtPeriodEnd;
  }

  public void setCancelAtPeriodEnd( final Boolean cancelAtPeriodEnd ) {
    this.cancelAtPeriodEnd = cancelAtPeriodEnd;
  }

  public Date getNextCaptureAt() {
    return this.nextCaptureAt;
  }

  public void setNextCaptureAt( final Date nextCaptureAt ) {
    this.nextCaptureAt = nextCaptureAt;
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

  public String getAppId() {
    return this.appId;
  }

  public void setAppId( final String appId ) {
    this.appId = appId;
  }

  public Date getTrialStart() {
    return this.trialStart;
  }

  @JsonIgnore
  public void setTrialStart( final Date trialStart ) {
    this.trialStart = trialStart;
  }

  public void setTrialStart( final long seconds ) {
    if( seconds > 0 )
      this.trialStart = new Date( seconds * 1000 );
  }

  public Date getTrialEnd() {
    return this.trialEnd;
  }

  @JsonIgnore
  public void setTrialEnd( final Date trialEnd ) {
    this.trialEnd = trialEnd;
  }

  public void setTrialEnd( final long seconds ) {
    if( seconds > 0 )
      this.trialEnd = new Date( seconds * 1000 );
  }

  public Date getCanceledAt() {
    return this.canceledAt;
  }

  @JsonIgnore
  public void setCanceledAt( final Date canceledAt ) {
    this.canceledAt = canceledAt;
  }

  public void setCanceledAt( final long seconds ) {
    if( seconds > 0 )
      this.canceledAt = new Date( seconds * 1000 );
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

    public Subscription.Filter byOfferId( final String offerId ) {
      this.offerId = offerId;
      return this;
    }

    public Subscription.Filter byCreatedAt( final Date startCreatedAt, final Date endCreatedAt ) {
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
