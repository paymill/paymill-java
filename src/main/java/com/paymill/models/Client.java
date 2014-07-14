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
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Client {

  private String             id;

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

  /**
   * Returns the unique identifier of this client.
   * @return {@link String}
   */
  public String getId() {
    return this.id;
  }

  /**
   * Sets the unique identifier of this client.
   * @param id
   *          {@link String}
   */
  public void setId( final String id ) {
    this.id = id;
  }

  /**
   * Returns the email address of this client.
   * @return {@link String} or <code>null</code>
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets the email address of this client.
   * @param email
   *          {@link String} or <code>null</code>
   */
  public void setEmail( final String email ) {
    this.email = email;
  }

  /**
   * Returns additional description for this client, perhaps the identifier from your CRM system?
   * @return {@link String} or <code>null</code>
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets additional description for this client, perhaps the identifier from your CRM system?
   * @param description
   *          {@link String} or <code>null</code>
   */
  public void setDescription( final String description ) {
    this.description = description;
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

  /**
   * Returns {@link List} of credit card/direct debit. Please note, that the payment objects might only contain valid ids.
   * @return {@link List} of {@link Payment}
   */
  public List<Payment> getPayments() {
    return this.payments;
  }

  /**
   * Sets {@link List} of {@link Payment}s.
   * @param payments
   *          {@link List}.
   */
  public void setPayments( final List<Payment> payments ) {
    this.payments = payments;
  }

  /**
   * Returns {@link List} of {@link Subscription}s.
   * @return {@link List} or <code>null</code>.
   */
  public List<Subscription> getSubscriptions() {
    return this.subscriptions;
  }

  /**
   * Sets {@link List} of {@link Subscription}s.
   * @param subscriptions
   */
  public void setSubscriptions( final List<Subscription> subscriptions ) {
    this.subscriptions = subscriptions;
  }

  /**
   * Returns App (ID) that created this client or <code>null</code> if created by yourself.
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this client or <code>null</code> if created by yourself.
   * @param appId
   *          {@link String}
   */
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

    @SnakeCase( "payment" )
    private String paymentId;

    @SnakeCase( "subscription" )
    private String subscriptionId;

    @SnakeCase( "offer" )
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

    /**
     * Creates filter for createdAt date. If endDate is given the filter is set for range from date to endDate. If endDate is
     * <code>null</code> the filter search for exact match.
     * @param date
     *          Start or exact date
     * @param endDate
     *          End date for the period or <code>null</code>.
     * @throws IllegalArgumentException
     *           When date is <code>null</code>.
     * @return {@link Client.Filter} object with populated filter for createdAt.
     */
    public Client.Filter byCreatedAt( final Date date, final Date endDate ) {
      this.createdAt = DateRangeBuilder.execute( date, endDate );
      return this;
    }

    /**
     * Creates filter for updatedAt date. If endDate is given the filter is set for range from date to endDate. If endDate is
     * <code>null</code> the filter search for exact match.
     * @param date
     *          Start or exact date
     * @param endDate
     *          End date for the period or <code>null</code>.
     * @throws IllegalArgumentException
     *           When date is <code>null</code>.
     * @return {@link Client.Filter} object with populated filter for updatedAt.
     */
    public Client.Filter byUpdatedAt( final Date date, final Date endDate ) {
      this.updatedAt = DateRangeBuilder.execute( date, endDate );
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
