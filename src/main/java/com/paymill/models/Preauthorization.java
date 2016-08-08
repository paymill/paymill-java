package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * If you’d like to reserve some money from the client’s credit card but you’d also like to execute the transaction itself a bit
 * later, then use preauthorizations. This is NOT possible with direct debit.
 *
 * <strong>A preauthorization is valid for 7 days.</strong>
 * @author Vassil Nikolov
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Preauthorization {

  private String                  id;

  private Integer                 amount;

  private String                  currency;

  private String                  description;

  private Preauthorization.Status status;

  private Boolean                 livemode;

  private Payment                 payment;

  private Client                  client;

  private Transaction             transaction;

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

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount( final Integer amount ) {
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

  public Transaction getTransaction() {
    return this.transaction;
  }

  public void setTransaction( final Transaction transaction ) {
    this.transaction = transaction;
  }

  public Client getClient() {
    return this.client;
  }

  public void setClient( final Client client ) {
    this.client = client;
  }

  /**
   * Returns App (ID) that created this preauthorization or <code>null</code> if created by yourself.
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this preauthorization or <code>null</code> if created by yourself.
   * @param appId
   *          {@link String}
   */
  public void setAppId( final String appId ) {
    this.appId = appId;
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

  public enum Status {
    PREAUTH("preauth"), OPEN("open"), CLOSED("closed"), DELETED("deleted"), FAILED("failed"), PENDING("pending"), UNDEFINED("undefined");

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
      return Status.UNDEFINED;
    }
  }

  public static Preauthorization.Filter createFilter() {
    return new Preauthorization.Filter();
  }

  public static Preauthorization.Order createOrder() {
    return new Preauthorization.Order();
  }

  public final static class Filter {

    @SnakeCase( "client" )
    private String clientId;

    @SnakeCase( "payment" )
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

    /**
     * Creates filter for createdAt date. If endDate is given the filter is set for range from date to endDate. If endDate is
     * <code>null</code> the filter search for exact match.
     * @param date
     *          Start or exact date
     * @param endDate
     *          End date for the period or <code>null</code>.
     * @throws IllegalArgumentException
     *           When date is <code>null</code>.
     * @return {@link Preauthorization.Filter} object with populated filter for createdAt.
     */
    public Preauthorization.Filter byCreatedAt( final Date date, final Date endDate ) {
      this.createdAt = DateRangeBuilder.execute( date, endDate );
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
