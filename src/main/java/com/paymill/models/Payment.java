package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The Payment object represents a payment with a credit card or via direct debit. It is used for several function calls (e.g.
 * transactions, subscriptions, clients, ...). To be PCI compliant these information is encoded by our Paymill PSP. You only get
 * in touch with safe data (token) and needn’t to care about the security problematic of informations like credit card data.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public final class Payment {

  public Payment() {
    super();
  }

  public Payment( final String id ) {
    this.id = id;
  }

  // NOTE[VNi]: Paymill supports two cards, namely direct debit and credit card

  // Common attributes:
  private String           id;

  private Payment.Type     type;

  @JsonProperty( "created_at" )
  private Date             createdAt;

  @JsonProperty( "updated_at" )
  private Date             updatedAt;

  @JsonProperty( "app_id" )
  private String           appId;

  // Direct debit attributes
  private String           code;

  private String           account;

  private String           holder;

  // Credit card attributes
  private Client           client;

  @JsonProperty( "card_type" )
  private Payment.CardType cardType;

  private String           country;

  @JsonProperty( "expire_month" )
  private Integer          expireMonth;

  @JsonProperty( "expire_year" )
  private Integer          expireYear;

  @JsonProperty( "card_holder" )
  private String           cardHolder;

  private String           last4;

  /**
   * Returns unique identifier for this credit card payment.
   * @return {@link String}
   */
  public String getId() {
    return this.id;
  }

  /**
   * Sets unique identifier for this credit card payment.
   * @param id
   */
  public void setId( final String id ) {
    this.id = id;
  }

  /**
   * Returns enumeration for credit card and direct debit
   * @return {@link Payment.Type}
   */
  public Payment.Type getType() {
    return this.type;
  }

  /**
   * Sets enumeration for credit card and direct debit
   * @param type
   *          {@link Payment.Type}
   */
  public void setType( final Payment.Type type ) {
    this.type = type;
  }

  /**
   * Returns App (ID) that created this payment or <code>null</code> if created by yourself.
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this payment or <code>null</code> if created by yourself.
   * @param appId
   *          {@link String}
   */
  public void setAppId( final String appId ) {
    this.appId = appId;
  }

  /**
   * Returns the used Bank Code
   * @return {@link String}
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Sets the used Bank Code.
   * @param code
   */
  public void setCode( final String code ) {
    this.code = code;
  }

  /**
   * Returns the used account number, for security reasons the number is masked.
   * @return {@link String}
   */
  public String getAccount() {
    return this.account;
  }

  /**
   * Sets the used account number, for security reasons the number is masked.
   * @param account
   *          {@link String}
   */
  public void setAccount( final String account ) {
    this.account = account;
  }

  /**
   * Returns name of the account holder.
   * @return
   */
  public String getHolder() {
    return this.holder;
  }

  /**
   * Sets name of the account holder.
   * @param holder
   *          {@link String}
   */
  public void setHolder( final String holder ) {
    this.holder = holder;
  }

  /**
   * The {@link Client}. Please note, that the client object might only contain a valid id.
   * @return {@link Client} or <code>null</code>.
   */
  public Client getClient() {
    return this.client;
  }

  /**
   * Sets the {@link Client}.
   * @param client
   *          {@link Client} or <code>null</code>
   */
  public void setClient( final Client client ) {
    this.client = client;
  }

  /**
   * Returns the card type eg. visa, mastercard.
   * @return {@link Payment.CardType}
   */
  public Payment.CardType getCardType() {
    return this.cardType;
  }

  /**
   * Sets the card type eg. visa, mastercard.
   * @param cardType
   *          {@link Payment.CardType}
   */
  public void setCardType( final Payment.CardType cardType ) {
    this.cardType = cardType;
  }

  /**
   * Returns the country.
   * @return {@link String}
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * Sets the country.
   * @param country
   *          {@link String}
   */
  public void setCountry( final String country ) {
    this.country = country;
  }

  /**
   * Returns the expiry month of the credit card.
   * @return {@link Integer}
   */
  public Integer getExpireMonth() {
    return this.expireMonth;
  }

  /**
   * Sets the expiry month of the credit card.
   * @param expireMonth
   *          {@link Integer}
   */
  public void setExpireMonth( final Integer expireMonth ) {
    this.expireMonth = expireMonth;
  }

  /**
   * Returns the expiry year of the credit card.
   * @return {@link Integer}
   */
  public Integer getExpireYear() {
    return this.expireYear;
  }

  /**
   * Sets the expiry year of the credit card.
   * @param expireYear
   *          {@link Integer}
   */
  public void setExpireYear( final Integer expireYear ) {
    this.expireYear = expireYear;
  }

  /**
   * Returns name of the card holder.
   * @return {@link String} or <code>null</code>
   */
  public String getCardHolder() {
    return this.cardHolder;
  }

  /**
   * Sets name of the card holder.
   * @param cardHolder
   *          {@link String} or <code>null</code>
   */
  public void setCardHolder( final String cardHolder ) {
    this.cardHolder = cardHolder;
  }

  /**
   * Returns the last four digits of the credit card.
   * @return {@link String}
   */
  public String getLast4() {
    return this.last4;
  }

  /**
   * Sets the last four digits of the credit card.
   * @param last4
   *          {@link String}
   */
  public void setLast4( final String last4 ) {
    this.last4 = last4;
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
    this.updatedAt = new Date( seconds * 1000 );
  }

  public static Payment.Filter createFilter() {
    return new Payment.Filter();
  }

  public static Payment.Order createOrder() {
    return new Payment.Order();
  }

  public enum CardType {
    VISA("visa"), MASTERCARD("mastercard"), MASTRO("maestro"), AMEX("amex"), JCB("jcb"),

    DINERS("diners"), DISCOVER("discover"), CHINA_UNION_PAY("china_union_pay"), UNKNOWN("unknown"), UNDEFINDED("undefined");

    private String value;

    private CardType( final String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static CardType create( final String value ) {
      for( CardType type : CardType.values() ) {
        if( type.getValue().equals( value ) ) {
          return type;
        }
      }
      return CardType.UNDEFINDED;
    }
  }

  public enum Type {

    CREDITCARD("creditcard"),

    DEBIT("debit"),

    UNDEFINDED("undefined");

    private String value;

    private Type( final String value ) {
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
      return Type.UNDEFINDED;
    }
  }

  public final static class Filter {

    @SnakeCase( "card_type" )
    private String cardType;

    @SnakeCase( "created_at" )
    private String createdAt;

    private Filter() {
      super();
    }

    public Payment.Filter byCardType( final Payment.CardType cardType ) {
      this.cardType = cardType.getValue();
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
     * @return {@link Payment.Filter} object with populated filter for createdAt.
     */
    public Payment.Filter byCreatedAt( final Date date, final Date endDate ) {
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

    public Payment.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Payment.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Payment.Order byCreatedAt() {
      this.createdAt = true;
      return this;
    }
  }

}
