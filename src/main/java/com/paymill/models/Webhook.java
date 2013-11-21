package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.paymill.models.Payment.Type;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Webhook {

  private String              id;

  @Updateable( "url" )
  private String              url;

  @Updateable( "email" )
  private String              email;

  @JsonProperty( "created_at" )
  private Date                createdAt;

  @JsonProperty( "updated_at" )
  private Date                updatedAt;

  @JsonProperty( "event_types" )
  private Webhook.EventType[] eventTypes;

  public Webhook() {
    super();
  }

  public Webhook( String id ) {
    this.id = id;
  }

  public void setCreatedAt( Date createdAt ) {
    this.createdAt = new Date( createdAt.getTime() * 1000 );
  }

  public static Webhook.Filter createFilter() {
    return new Webhook.Filter();
  }

  public static Webhook.Order createOrder() {
    return new Webhook.Order();
  }

  public final static class Filter {

    @SnakeCase( "url" )
    private String url;

    @SnakeCase( "email" )
    private String email;

    @SnakeCase( "created_at" )
    private String createdAt;

    private Filter() {
      super();
    }

    public Webhook.Filter byUrl( String url ) {
      this.url = url;
      return this;
    }

    public Webhook.Filter byEmail( String email ) {
      this.email = email;
      return this;
    }

    public Webhook.Filter byCreatedAt( Date startCreatedAt, Date endCreatedAt ) {
      this.createdAt = String.valueOf( startCreatedAt.getTime() ) + "-" + String.valueOf( endCreatedAt.getTime() );
      return this;
    }

  }

  public final static class Order {

    @SnakeCase( "url" )
    private boolean url;

    @SnakeCase( "email" )
    private boolean email;

    @SnakeCase( "created_at" )
    private boolean createdAt;

    @SnakeCase( value = "asc", order = true )
    private boolean asc;

    @SnakeCase( value = "desc", order = true )
    private boolean desc;

    private Order() {
      super();
    }

    public Webhook.Order asc() {
      this.asc = true;
      this.desc = false;
      return this;
    }

    public Webhook.Order desc() {
      this.asc = false;
      this.desc = true;
      return this;
    }

    public Webhook.Order byCreatedAt() {
      this.email = false;
      this.createdAt = true;
      this.url = false;
      return this;
    }

    public Webhook.Order byUrl() {
      this.email = false;
      this.createdAt = false;
      this.url = true;
      return this;
    }

    public Webhook.Order byEmail() {
      this.email = true;
      this.createdAt = false;
      this.url = false;
      return this;
    }

  }

  public enum EventType {

    /**
     * Returns a {@link Transaction} with state set to chargeback.
     */
    CHARGEBACK_EXECUTED("chargeback.executed"),

    /**
     * Returns a {@link Transaction}.
     */
    TRANSACTION_CREATED("transaction.created"),

    /**
     * Returns a {@link Transaction}.
     */
    TRANSACTION_SUCCEEDED("transaction.succeeded"),

    /**
     * returns a {@link Transaction}.
     */
    TRANSACTION_FAILED(""),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_CREATED("subscription.created"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_UPDATED("subscription.updated"),

    /**
     * Returns a {@link Subscription}.
     */
    SUBSCRIPTION_DELETED("subscription.deleted"),

    /**
     * Returns a {@link Transaction} and a {@link Subscription}.
     */
    SUBSCRIPTION_SUCCEEDED("subscription.succeeded"),

    /**
     * Returns a {@link Transaction} and a {@link Subscription}.
     */
    SUBSCRIPTION_FAILED("subscription.failed"),

    /**
     * Returns a refund.
     */
    REFUND_CREATED("refund.created"),

    /**
     * Returns a {@link Refund}.
     */
    REFUND_SUCCEEDED("refund.succeeded"),

    /**
     * Returns a {@link Refund}.
     */
    REFUND_FAILED("refund.failed"),

    /**
     * Returns an invoice with the payout sum for the invoice period.
     */
    PAYOUT_TRANSFERRED("payout.transferred"),

    /**
     * Returns an invoice with the fees sum for the invoice period.
     */
    INVOICE_AVAILABLE("invoice.available"),

    /**
     * Returns a merchant if a connected merchant was activated.
     */
    APP_MERCHANT_ACTIVATED("app.merchant.activated"),

    /**
     * Returns a merchant if a connected merchant was deactivated.
     */
    APP_MERCHANT_DEACTIVATED("app.merchant.deactivated"),

    /**
     * Returns a merchant if a connected merchant was rejected.
     */
    APP_MERCHANT_REJECTED("app.merchant.rejected"),

    /**
     * Returns a {@link Client} if a {@link Client} was updated.
     */
    CLIENT_UPDATED("client.updated");

    private String value;

    private EventType( String value ) {
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
      throw new IllegalArgumentException( "Invalid value for Webhook.EventType" );
    }

  }

}
