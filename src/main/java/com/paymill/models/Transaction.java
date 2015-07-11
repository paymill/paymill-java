package com.paymill.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A transaction is the charging of a credit card or a direct debit. In this case you need a new transaction object with either a
 * valid token, payment, client + payment or preauthorization. Every transaction has a unique identifier which will be generated
 * by PAYMILL to identify every transaction. You can issue/create, list and display transactions in detail. Refunds can be done in
 * an extra entity.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Transaction {

  private String             id;

  private Integer            amount;

  @JsonProperty( "origin_amount" )
  private Integer            originAmount;

  private String             currency;

  private Transaction.Status status;

  @Updateable( "description" )
  private String             description;

  private Boolean            livemode;

  private List<Refund>       refunds;

  private Payment            payment;

  private Client             client;

  private Preauthorization   preauthorization;

  @JsonProperty( "created_at" )
  private Date               createdAt;

  @JsonProperty( "updated_at" )
  private Date               updatedAt;

  @JsonProperty( "response_code" )
  private Integer            responseCode;

  @JsonProperty( "short_id" )
  private String             shortId;

  @JsonProperty( "is_fraud" )
  private Boolean            fraud;

  private List<Fee>          fees;

  @JsonProperty( "app_id" )
  private String             appId;

  @JsonProperty( "mandate_reference" )
  private String                       mandateReference;

  public Transaction() {
    super();
  }

  public Transaction( final String id ) {
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

  public Integer getOriginAmount() {
    return this.originAmount;
  }

  public void setOriginAmount( final Integer originAmount ) {
    this.originAmount = originAmount;
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency( final String currency ) {
    this.currency = currency;
  }

  public Transaction.Status getStatus() {
    return this.status;
  }

  public void setStatus( final Transaction.Status status ) {
    this.status = status;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription( final String description ) {
    this.description = description;
  }

  public Boolean getLivemode() {
    return this.livemode;
  }

  public void setLivemode( final Boolean livemode ) {
    this.livemode = livemode;
  }

  public List<Refund> getRefunds() {
    return this.refunds;
  }

  public void setRefunds( final List<Refund> refunds ) {
    this.refunds = refunds;
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

  public Preauthorization getPreauthorization() {
    return this.preauthorization;
  }

  public void setPreauthorization( final Preauthorization preauthorization ) {
    this.preauthorization = preauthorization;
  }

  public Integer getResponseCode() {
    return this.responseCode;
  }

  public void setResponseCode( final Integer responseCode ) {
    this.responseCode = responseCode;
  }

  /**
   * Returns the response code as detailed message.
   * @return the message or <code>null</code> if no message is available.
   */
  public String getResponseCodeDetail() {
    if( getResponseCode() == 10001 ) {
      return "General undefined response";
    } else if( getResponseCode() == 10002 ) {
      return "Still waiting on something.";
    } else if( getResponseCode() == 20000 ) {
      return "General success response.";
    } else if( getResponseCode() == 40000 ) {
      return "General problem with data.";
    } else if( getResponseCode() == 40001 ) {
      return "General problem with payment data.";
    } else if( getResponseCode() == 40100 ) {
      return "Problem with credit card data.";
    } else if( getResponseCode() == 40101 ) {
      return "Problem with cvv.";
    } else if( getResponseCode() == 40102 ) {
      return "Card expired or not yet valid.";
    } else if( getResponseCode() == 40103 ) {
      return "Limit exceeded.";
    } else if( getResponseCode() == 40104 ) {
      return "Card invalid.";
    } else if( getResponseCode() == 40105 ) {
      return "Expiry date not valid.";
    } else if( getResponseCode() == 40106 ) {
      return "Credit card brand required.";
    } else if( getResponseCode() == 40200 ) {
      return "Problem with bank account data.";
    } else if( getResponseCode() == 40201 ) {
      return "Bank account data combination mismatch.";
    } else if( getResponseCode() == 40202 ) {
      return "User authentication failed.";
    } else if( getResponseCode() == 40300 ) {
      return "Problem with 3d secure data.";
    } else if( getResponseCode() == 40301 ) {
      return "Currency / amount mismatch";
    } else if( getResponseCode() == 40400 ) {
      return "Problem with input data.";
    } else if( getResponseCode() == 40401 ) {
      return "Amount too low or zero.";
    } else if( getResponseCode() == 40402 ) {
      return "Usage field too long.";
    } else if( getResponseCode() == 40403 ) {
      return "Currency not allowed.";
    } else if( getResponseCode() == 50000 ) {
      return "General problem with backend.";
    } else if( getResponseCode() == 50001 ) {
      return "Country blacklisted.";
    } else if( getResponseCode() == 50002 ) {
      return "IP address blacklisted.";
    } else if( getResponseCode() == 50003 ) {
      return "Anonymous IP proxy used.";
    } else if( getResponseCode() == 50100 ) {
      return "Technical error with credit card.";
    } else if( getResponseCode() == 50101 ) {
      return "Error limit exceeded.";
    } else if( getResponseCode() == 50102 ) {
      return "Card declined by authorization system.";
    } else if( getResponseCode() == 50103 ) {
      return "Manipulation or stolen card.";
    } else if( getResponseCode() == 50104 ) {
      return "Card restricted.";
    } else if( getResponseCode() == 50105 ) {
      return "Invalid card configuration data.";
    } else if( getResponseCode() == 50200 ) {
      return "Technical error with bank account.";
    } else if( getResponseCode() == 50201 ) {
      return "Card blacklisted.";
    } else if( getResponseCode() == 50300 ) {
      return "Technical error with 3D secure.";
    } else if( getResponseCode() == 50400 ) {
      return "Decline because of risk issues.";
    } else if( getResponseCode() == 50500 ) {
      return "General timeout.";
    } else if( getResponseCode() == 50501 ) {
      return "Timeout on side of the acquirer.";
    } else if( getResponseCode() == 50502 ) {
      return "Risk management transaction timeout.";
    } else if( getResponseCode() == 50600 ) {
      return "Duplicate transaction.";
    } else {
      return null;
    }

  }

  /**
   * Checks if the transaction was successful. Utility method, checks if the response_code is 20000.
   * @return true if successful, false otherwise
   */
  public boolean isSuccessful() {
    return getResponseCode() == 20000;
  }

  public String getShortId() {
    return this.shortId;
  }

  public void setShortId( final String shortId ) {
    this.shortId = shortId;
  }

  public Boolean getFraud() {
    return this.fraud;
  }

  public void setFraud( final Boolean fraud ) {
    this.fraud = fraud;
  }

  public List<Fee> getFees() {
    return this.fees;
  }

  public void setFees( final List<Fee> fees ) {
    this.fees = fees;
  }

  /**
   * Returns App (ID) that created this transaction or <code>null</code> if created by yourself.
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this transaction or <code>null</code> if created by yourself.
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

  public static Transaction.Filter createFilter() {
    return new Transaction.Filter();
  }

  public static Transaction.Order createOrder() {
    return new Transaction.Order();
  }

  public final static class Filter {

    @SnakeCase( "client" )
    private String clientId;

    @SnakeCase( "payment" )
    private String paymentId;

    @SnakeCase( "amount" )
    private String amount;

    @SnakeCase( "description" )
    private String description;

    @SnakeCase( "created_at" )
    private String createdAt;

    @SnakeCase( "updated_at" )
    private String updatedAt;

    @SnakeCase( "status" )
    private String status;

    private Filter() {
      super();
    }

    public Transaction.Filter byClientId( final String clientId ) {
      this.clientId = clientId;
      return this;
    }

    public Transaction.Filter byPaymentId( final String paymentId ) {
      this.paymentId = paymentId;
      return this;
    }

    public Transaction.Filter byAmount( final int amount ) {
      this.amount = String.valueOf( amount );
      return this;
    }

    public Transaction.Filter byAmountGreaterThan( final int amount ) {
      this.amount = ">" + String.valueOf( amount );
      return this;
    }

    public Transaction.Filter byAmountLessThan( final int amount ) {
      this.amount = "<" + String.valueOf( amount );
      return this;
    }

    public Transaction.Filter byDescription( final String description ) {
      this.description = description;
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
     * @return {@link Transaction.Filter} object with populated filter for createdAt.
     */
    public Transaction.Filter byCreatedAt( final Date date, final Date endDate ) {
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
     * @return {@link Transaction.Filter} object with populated filter for updatedAt.
     */
    public Transaction.Filter byUpdatedAt( final Date date, final Date endDate ) {
      this.updatedAt = DateRangeBuilder.execute( date, endDate );
      return this;
    }

    public Transaction.Filter byStatus( final Transaction.Status status ) {
      this.status = status.getValue();
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

    public Transaction.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Transaction.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Transaction.Order byCreatedAt() {
      this.createdAt = true;
      return this;
    }

  }

  public enum Status {

    OPEN("open"),

    PENDING("pending"),

    CLOSED("closed"),

    FAILED("failed"),

    PARTIAL_REFUNDED("partial_refunded"),

    REFUNDED("refunded"),

    PREAUTH("preauth"),

    CHARGEBACK("chargeback"),

    UNDEFINED("undefined");

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

}
