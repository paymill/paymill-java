package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Subscriptions allow you to charge recurring payments on a client’s credit card / to a client’s direct debit. A subscription
 * connects a client to the {@link Offer}s. A client can have several subscriptions to different offers, but only one subscription
 * to the same offer.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Subscription {

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

  public Subscription() {
    super();
  }

  public Subscription( final String id ) {
    this.id = id;
  }

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

  /**
   * Returns App (ID) that created this subscription or <code>null</code> if created by yourself.
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this subscription or <code>null</code> if created by yourself.
   * @param appId
   *          {@link String}
   */
  public void setAppId( final String appId ) {
    this.appId = appId;
  }

  /**
   * Returns the next captured date.
   * @return {@link Date}
   */
  public Date getNextCaptureAt() {
    return this.nextCaptureAt;
  }

  /**
   * Set the next capture date.
   * @param nextCaptureAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setNextCaptureAt( final Date nextCaptureAt ) {
    this.nextCaptureAt = nextCaptureAt;
  }

  /**
   * Set the next capture date.
   * @param seconds
   *          Next capture date representation is seconds.
   */
  public void setNextCaptureAt( final long seconds ) {
    if( seconds > 0 )
      this.nextCaptureAt = new Date( seconds * 1000 );
  }

  /**
   * Returns the trial start date.
   * @return {@link Date}
   */
  public Date getTrialStart() {
    return this.trialStart;
  }

  /**
   * Set the trial start date.
   * @param trialStart
   *          {@link Date}
   */
  @JsonIgnore
  public void setTrialStart( final Date trialStart ) {
    this.trialStart = trialStart;
  }

  /**
   * Set the trial start date.
   * @param seconds
   *          Trial start date representation is seconds.
   */
  public void setTrialStart( final long seconds ) {
    if( seconds > 0 )
      this.trialStart = new Date( seconds * 1000 );
  }

  /**
   * Returns the trial end date.
   * @return {@link Date}
   */
  public Date getTrialEnd() {
    return this.trialEnd;
  }

  /**
   * Set the trial end date.
   * @param trialEnd
   *          {@link Date}
   */
  @JsonIgnore
  public void setTrialEnd( final Date trialEnd ) {
    this.trialEnd = trialEnd;
  }

  /**
   * Set the trial end date.
   * @param seconds
   *          Trial end date representation is seconds.
   */
  public void setTrialEnd( final long seconds ) {
    if( seconds > 0 )
      this.trialEnd = new Date( seconds * 1000 );
  }

  /**
   * Returns the cancellation date.
   * @return {@link Date}
   */
  public Date getCanceledAt() {
    return this.canceledAt;
  }

  /**
   * Set the cancellation date.
   * @param canceledAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setCanceledAt( final Date canceledAt ) {
    this.canceledAt = canceledAt;
  }

  /**
   * Set the cancellation date.
   * @param seconds
   *          Cancellation date representation is seconds.
   */
  public void setCanceledAt( final long seconds ) {
    if( seconds > 0 )
      this.canceledAt = new Date( seconds * 1000 );
  }

  /**
   * Returns the creation date.
   * @return {@link Date}
   */
  public Date getCreatedAt() {
    return this.createdAt;
  }

  /**
   * Set the creation date.
   * @param createdAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setCreatedAt( final Date createdAt ) {
    this.createdAt = createdAt;
  }

  /**
   * Set the creation date.
   * @param seconds
   *          Creation date representation is seconds.
   */
  public void setCreatedAt( final long seconds ) {
    this.createdAt = new Date( seconds * 1000 );
  }

  /**
   * Returns the last update.
   * @return {@link Date}
   */
  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  /**
   * Sets the last update.
   * @param updatedAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setUpdatedAt( final Date updatedAt ) {
    this.updatedAt = updatedAt;
  }

  /**
   * Sets the last update.
   * @param seconds
   *          Last update representation is seconds.
   */
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

    @SnakeCase( "offer" )
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
