package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.paymill.models.Fee.Type;

/**
 * Subscriptions allow you to charge recurring payments on a client’s credit card / to a client’s direct debit. A subscription
 * connects a client to the {@link Offer}s. A client can have several subscriptions to different offers, but only one subscription
 * to the same offer.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Subscription {

  private String                       id;

  private Offer                        offer;

  private Boolean                      livemode;

  private Integer                      amount;

  @JsonProperty( "temp_amount" )
  private Integer                      tempAmount;

  @Updateable( "currency" )
  private String                       currency;

  @Updateable( "name" )
  private String                       name;

  @Updateable( "interval" )
  private Interval.PeriodWithChargeDay interval;

  @JsonProperty( "trial_start" )
  private Date                         trialStart;

  @JsonProperty( "trial_end" )
  private Date                         trialEnd;

  @JsonProperty( "period_of_validity" )
  private Interval.Period              periodOfValidity;

  @JsonProperty( "end_of_period" )
  private Date                         endOfPeriod;

  @JsonProperty( "next_capture_at" )
  private Date                         nextCaptureAt;

  @JsonProperty( "created_at" )
  private Date                         createdAt;

  @JsonProperty( "updated_at" )
  private Date                         updatedAt;

  @JsonProperty( "canceled_at" )
  private Date                         canceledAt;

  @Updateable( "payment" )
  private Payment                      payment;

  private Client                       client;

  @JsonProperty( "app_id" )
  private String                       appId;

  private Subscription.Status          status;

  @JsonProperty( "is_canceled" )
  private Boolean                      canceled;

  @JsonProperty( "is_deleted" )
  private Boolean                      deleted;

  @JsonProperty( "mandate_reference" )
  private String                       mandateReference;

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

  public Integer getAmount() {
    return amount;
  }

  public void setAmount( Integer amount ) {
    this.amount = amount;
  }

  public Integer getTempAmount() {
    return tempAmount;
  }

  public void setTempAmount( Integer tempAmount ) {
    this.tempAmount = tempAmount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency( String currency ) {
    this.currency = currency;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public Interval.PeriodWithChargeDay getInterval() {
    return interval;
  }

  public void setInterval( Interval.PeriodWithChargeDay interval ) {
    this.interval = interval;
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

  public Interval.Period getPeriodOfValidity() {
    return periodOfValidity;
  }

  public void setPeriodOfValidity( Interval.Period periodOfValidity ) {
    this.periodOfValidity = periodOfValidity;
  }

  public Date getEndOfPeriod() {
    return endOfPeriod;
  }

  public void setEndOfPeriod( Date endOfPeriod ) {
    this.endOfPeriod = endOfPeriod;
  }

  public Subscription.Status getStatus() {
    return status;
  }

  public void setStatus( Subscription.Status status ) {
    this.status = status;
  }

  public Boolean getCanceled() {
    return canceled;
  }

  public void setCanceled( Boolean canceled ) {
    this.canceled = canceled;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted( Boolean deleted ) {
    this.deleted = deleted;
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
   * Set the mandate reference. SEPA mandate reference, can be optionally specified for direct debit transactions.
   * @param mandateReference
   *          {@link String}
   */
  public void setMandateReference( final String mandateReference ) {
    this.mandateReference = mandateReference;
  }

  /**
   * Returns the mandate reference.
   * @return {@link String}
   */
  public String getMandateReference() {
    return this.mandateReference;
  }

  /**
   * Set the creation date.
   * @param seconds
   *          Creation date representation is seconds.
   */
  public void setCreatedAt( final long seconds ) {
    if( seconds > 0 )
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
    if( seconds > 0 )
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

    /**
     * Creates filter for createdAt date. If endDate is given the filter is set for range from date to endDate. If endDate is
     * <code>null</code> the filter search for exact match.
     * @param date
     *          Start or exact date
     * @param endDate
     *          End date for the period or <code>null</code>.
     * @throws IllegalArgumentException
     *           When date is <code>null</code>.
     * @return {@link Subscription.Filter} object with populated filter for createdAt.
     */
    public Subscription.Filter byCreatedAt( final Date date, final Date endDate ) {
      this.createdAt = DateRangeBuilder.execute( date, endDate );
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

  public enum Status {

    ACTIVE("active"), INACTIVE("inactive"), FAILED("failed"), EXPIRED("expired");

    private String value;

    private Status( final String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Type create( final String value ) {
      for( Type type : Type.values() ) {
        if( type.getValue().equals( value ) ) {
          return type;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Subscription.status:" + value + ". An update of paymill-java is recommended" );
    }
  }

  public static Creator create( Payment payment, Integer amount, String currency, String interval ) {
    return new Creator( payment, amount, currency, new Interval.PeriodWithChargeDay( interval ) );

  }

  public static Creator create( String paymentId, Integer amount, String currency, String interval ) {
    return new Creator( new Payment( paymentId ), amount, currency, new Interval.PeriodWithChargeDay( interval ) );

  }

  public static Creator create( Payment payment, Integer amount, String currency, Interval.PeriodWithChargeDay interval ) {
    return new Creator( payment, amount, currency, interval );

  }

  public static Creator create( String paymentId, Integer amount, String currency, Interval.PeriodWithChargeDay interval ) {
    return new Creator( new Payment( paymentId ), amount, currency, interval );
  }

  public static Creator create( Payment payment, Offer offer ) {
    return new Creator( payment, offer );
  }

  public static Creator create( String paymentId, Offer offer ) {
    return new Creator( new Payment( paymentId ), offer );

  }

  public static Creator create( String paymentId, String offerId ) {
    return new Creator( new Payment( paymentId ), new Offer( offerId ) );

  }

  public static Creator create( Payment payment, String offerId ) {
    return new Creator( payment, new Offer( offerId ) );

  }

  /**
   * Due to the large number of optional parameters, this class is the recommended way to create subscriptions
   */
  public static class Creator {

    private Payment                      payment;
    private Client                       client;
    private Offer                        offer;
    private Integer                      amount;
    private String                       currency;
    private Interval.PeriodWithChargeDay interval;
    private Date                         startAt;
    private String                       name;
    private Interval.Period              periodOfValidity;

    private Creator( Payment payment, Integer amount, String currency, Interval.PeriodWithChargeDay interval ) {
      this.payment = payment;
      this.amount = amount;
      this.currency = currency;
      this.interval = interval;
    }

    private Creator( Payment payment, Offer offer ) {
      this.payment = payment;
      this.offer = offer;
    }

    public Creator withClient( Client client ) {
      this.client = client;
      return this;
    }

    public Creator withClient( String clientId ) {
      this.client = new Client( clientId );
      return this;
    }

    public Creator withOffer( Offer offer ) {
      this.offer = offer;
      return this;
    }

    public Creator withOffer( String offerId ) {
      this.offer = new Offer( offerId );
      return this;
    }

    public Creator withAmount( Integer amount ) {
      this.amount = amount;
      return this;
    }

    public Creator withCurrency( String currency ) {
      this.currency = currency;
      return this;
    }

    public Creator withInterval( Interval.PeriodWithChargeDay interval ) {
      this.interval = interval;
      return this;
    }

    public Creator withInterval( String interval ) {
      this.interval = new Interval.PeriodWithChargeDay( interval );
      return this;
    }

    public Creator withStartDate( Date startAt ) {
      this.startAt = startAt;
      return this;
    }

    public Creator withName( String name ) {
      this.name = name;
      return this;
    }

    public Creator withPeriodOfValidity( Interval.Period period ) {
      this.periodOfValidity = period;
      return this;
    }

    public Creator withPeriodOfValidity( String period ) {
      this.periodOfValidity = new Interval.Period( period );
      return this;
    }

    public Payment getPayment() {
      return payment;
    }

    public void setPayment( Payment payment ) {
      this.payment = payment;
    }

    public Client getClient() {
      return client;
    }

    public void setClient( Client client ) {
      this.client = client;
    }

    public Offer getOffer() {
      return offer;
    }

    public void setOffer( Offer offer ) {
      this.offer = offer;
    }

    public Integer getAmount() {
      return amount;
    }

    public void setAmount( Integer amount ) {
      this.amount = amount;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency( String currency ) {
      this.currency = currency;
    }

    public Interval.PeriodWithChargeDay getInterval() {
      return interval;
    }

    public void setInterval( Interval.PeriodWithChargeDay interval ) {
      this.interval = interval;
    }

    public Date getStartAt() {
      return startAt;
    }

    public void setStartAt( Date startAt ) {
      this.startAt = startAt;
    }

    public String getName() {
      return name;
    }

    public void setName( String name ) {
      this.name = name;
    }

    public Interval.Period getPeriodOfValidity() {
      return periodOfValidity;
    }

    public void setPeriodOfValidity( Interval.Period periodOfValidity ) {
      this.periodOfValidity = periodOfValidity;
    }

  }
}
