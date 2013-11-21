package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Payment {

  public Payment() {
    super();
  }

  public Payment( String id ) {
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
  private String           client;

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

  public void setCreatedAt( Date createdAt ) {
    this.createdAt = new Date( createdAt.getTime() * 1000 );
  }

  public enum Type {

    CREDITCARD("creditcard"),

    DEBIT("debit");

    private String value;

    private Type( String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Type create( String value ) {
      for( Type type : Type.values() ) {
        if( type.getValue().equals( value ) ) {
          return type;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Payment.Type" );
    }
  }

  public enum CardType {
    VISA("visa"), MASTERCARD("mastercard"), MASTRO("maestro"), AMEX("amex"), JCB("jcb"),

    DINERS("diners"), DISCOVER("discover"), CHINA_UNION_PAY("china_union_pay"), UNKNOWN("unknown");

    private String value;

    private CardType( String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Type create( String value ) {
      for( Type type : Type.values() ) {
        if( type.getValue().equals( value ) ) {
          return type;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Payment.CardType" );
    }
  }

  public static Payment.Filter createFilter() {
    return new Payment.Filter();
  }

  public static Payment.Order createOrder() {
    return new Payment.Order();
  }

  public final static class Filter {

    @SnakeCase( "card_type" )
    private String cardType;

    @SnakeCase( "created_at" )
    private String createdAt;

    private Filter() {
      super();
    }

    public Payment.Filter byCardType( Payment.CardType cardType ) {
      this.cardType = cardType.getValue();
      return this;
    }

    public Payment.Filter byCreatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
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
