package com.paymill.models;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An offer is a recurring plan which a user can subscribe to. You can create different offers with different plan attributes e.g.
 * a monthly or a yearly based paid offer/plan.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Offer {

  public Offer() {
    super();
  }

  public Offer( String id ) {
    this.id = id;
  }

  private String                  id;

  @Updateable( "name" )
  private String                  name;

  private Integer                 amount;

  private Interval                interval;

  @JsonProperty( "trial_period_days" )
  private Integer                 trialPeriodDays;

  private String                  currency;

  @JsonProperty( "created_at" )
  private Date                    createdAt;

  @JsonProperty( "updated_at" )
  private Date                    updatedAt;

  @JsonProperty( "app_id" )
  private String                  appId;

  @JsonProperty( "subscription_count" )
  private Offer.SubscriptionCount subscriptionCount;

  public String getId() {
    return id;
  }

  public void setId( String id ) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount( Integer amount ) {
    this.amount = amount;
  }

  public Integer getTrialPeriodDays() {
    return trialPeriodDays;
  }

  public void setTrialPeriodDays( Integer trialPeriodDays ) {
    this.trialPeriodDays = trialPeriodDays;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency( String currency ) {
    this.currency = currency;
  }

  /**
   * Returns App (ID) that created this offer or <code>null</code> if created by yourself.
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this offer or <code>null</code> if created by yourself.
   * @param appId
   *          {@link String}
   */
  public void setAppId( String appId ) {
    this.appId = appId;
  }

  public Interval getInterval() {
    return interval;
  }

  public void setInterval( String interval ) {
    this.interval = new Interval( interval );
  }

  public Offer.SubscriptionCount getSubscriptionCount() {
    return this.subscriptionCount;
  }

  public void setSubscriptionCount( final Offer.SubscriptionCount subscriptionCount ) {
    this.subscriptionCount = subscriptionCount;
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

  public static Offer.Filter createFilter() {
    return new Offer.Filter();
  }

  public static Offer.Order createOrder() {
    return new Offer.Order();
  }

  @JsonIgnoreProperties( ignoreUnknown = true )
  public class Interval {

    private Integer    interval;
    private Offer.Unit unit;

    public Interval( final String interval ) {
      String[] parts = StringUtils.split( interval );
      this.interval = Integer.parseInt( parts[0] );
      this.unit = Offer.Unit.create( parts[1] );
    }

    public Integer getInterval() {
      return this.interval;
    }

    public void setInterval( final Integer interval ) {
      this.interval = interval;
    }

    public Offer.Unit getUnit() {
      return this.unit;
    }

    public void setUnit( final Offer.Unit unit ) {
      this.unit = unit;
    }

    @Override
    public String toString() {
      return this.interval + " " + this.unit;
    }

  }

  public enum Unit {
    DAY("DAY"), WEEK("WEEK"), MONTH("MONTH"), YEAR("YEAR");

    private String value;

    private Unit( final String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Unit create( final String value ) {
      for( Unit unit : Unit.values() ) {
        if( unit.getValue().equals( value ) ) {
          return unit;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Interval.Unit" );
    }
  }

  @JsonIgnoreProperties( ignoreUnknown = true )
  public class SubscriptionCount {
    private String  active;
    private Integer inactive;

    public String getActive() {
      return this.active;
    }

    public void setActive( final String active ) {
      this.active = active;
    }

    public Integer getInactive() {
      return this.inactive;
    }

    public void setInactive( final Integer inactive ) {
      this.inactive = inactive;
    }

  }

  public final static class Filter {

    @SnakeCase( "name" )
    private String name;

    @SnakeCase( "trial_period_days" )
    private String trialPeriodDays;

    @SnakeCase( "amount" )
    private String amount;

    @SnakeCase( "created_at" )
    private String createdAt;

    @SnakeCase( "updated_at" )
    private String updatedAt;

    private Filter() {
      super();
    }

    public Offer.Filter byName( String name ) {
      this.name = name;
      return this;
    }

    public Offer.Filter byTrialPeriodDays( Integer trialPeriodDays ) {
      this.trialPeriodDays = String.valueOf( trialPeriodDays );
      return this;
    }

    public Offer.Filter byAmount( final int amount ) {
      this.amount = String.valueOf( amount );
      return this;
    }

    public Offer.Filter byAmountGreaterThan( final int amount ) {
      this.amount = ">" + String.valueOf( amount );
      return this;
    }

    public Offer.Filter byAmountLessThan( final int amount ) {
      this.amount = "<" + String.valueOf( amount );
      return this;
    }

    public Offer.Filter byCreatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }

    public Offer.Filter byUpdatedAt( Date startUpdatedAt, Date endUpdatedAt ) {
      this.updatedAt = String.valueOf( startUpdatedAt.getTime() ) + "-" + String.valueOf( endUpdatedAt.getTime() );
      return this;
    }
  }

  public final static class Order {

    @SnakeCase( "interval" )
    private boolean interval;

    @SnakeCase( "amount" )
    private boolean amount;

    @SnakeCase( "created_at" )
    private boolean createdAt;

    @SnakeCase( "trial_period_days" )
    private boolean trialPeriodDays;

    @SnakeCase( value = "asc", order = true )
    private boolean asc;

    @SnakeCase( value = "desc", order = true )
    private boolean desc;

    private Order() {
      super();
    }

    public Offer.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Offer.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Offer.Order byInterval() {
      this.interval = true;
      this.amount = false;
      this.createdAt = false;
      this.trialPeriodDays = false;
      return this;
    }

    public Offer.Order byAmount() {
      this.interval = false;
      this.amount = true;
      this.createdAt = false;
      this.trialPeriodDays = false;
      return this;
    }

    public Offer.Order byCreatedAt() {
      this.interval = false;
      this.amount = false;
      this.createdAt = true;
      this.trialPeriodDays = false;
      return this;
    }

    public Offer.Order byTrialPeriodDays() {
      this.interval = false;
      this.amount = false;
      this.createdAt = true;
      this.trialPeriodDays = false;
      return this;
    }
  }

}
