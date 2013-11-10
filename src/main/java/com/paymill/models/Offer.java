package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An offer is a recurring plan which a user can subscribe to. You can create different offers with different plan attributes e.g.
 * a monthly or a yearly based paid offer/plan.
 * @author Vassil Nikolov
 */
@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Offer {
  //TODO[VNi]: handle subscription_count

  public Offer() {
    super();
  }

  public Offer( String id ) {
    this.id = id;
  }

  private String   id;

  @Updateable
  private String   name;

  private Integer  amount;

  private Interval interval;

  @JsonProperty( "trial_period_days" )
  private Integer  trialPeriodDays;

  private String   currency;

  @JsonProperty( "created_at" )
  private Date     createdAt;

  @JsonProperty( "updated_at" )
  private Date     updatedAt;

  @JsonProperty( "app_id" )
  private String   appId;

  public void setInterval( String interval ) {
    this.interval = new Interval( interval );
  }

  public static Offer.Filter createFilter() {
    return new Offer.Filter();
  }

  public static Offer.Order createOrder() {
    return new Offer.Order();
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

    public Offer.Filter byAmount( Integer amount ) {
      this.amount = String.valueOf( amount );
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
