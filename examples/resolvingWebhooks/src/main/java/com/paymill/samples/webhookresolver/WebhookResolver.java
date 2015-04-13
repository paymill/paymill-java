package com.paymill.samples.webhookresolver;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymill.context.PaymillContext;
import com.paymill.models.Client;
import com.paymill.models.Invoice;
import com.paymill.models.Merchant;
import com.paymill.models.Payment;
import com.paymill.models.Refund;
import com.paymill.models.Subscription;
import com.paymill.models.Transaction;
import com.paymill.models.Webhook;
import com.paymill.models.Webhook.EventType;

/**
 * You can use this object to deserialize incoming webhooks.
 */
public class WebhookResolver {

  private Webhook.EventType eventType;

  private Client            client;
  private Transaction       transaction;
  private Subscription      subscription;
  private Refund            refund;
  private Payment           payment;
  private Invoice           invoice;
  private Merchant          merchant;

  private Date              createdAt;
  private String            appId;

  public Webhook.EventType getEventType() {
    return eventType;
  }

  public Client getClient() {
    return client;
  }

  public Transaction getTransaction() {
    return transaction;
  }

  public Subscription getSubscription() {
    return subscription;
  }

  public Refund getRefund() {
    return refund;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public String getAppId() {
    return appId;
  }

  public Payment getPayment() {
    return payment;
  }

  public Invoice getInvoice() {
    return invoice;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public static WebhookResolver fromString( String requestBody ) {
    try {
      WebhookResolver resolver = new WebhookResolver();
      JsonNode eventNode = PaymillContext.PARSER.readValue( requestBody, JsonNode.class ).get( "event" );
      if( eventNode != null && eventNode.has( "event_type" ) ) {
        resolver.eventType = PaymillContext.PARSER.readValue( eventNode.get( "event_type" ).toString(), Webhook.EventType.class );
      } else {
        throw new RuntimeException( "Invalid webhook:" + requestBody );
      }
      JsonNode eventResource = eventNode.get( "event_resource" );

      if( resolver.getEventType() == EventType.CHARGEBACK_EXECUTED || resolver.getEventType() == EventType.TRANSACTION_CREATED
          || resolver.getEventType() == EventType.TRANSACTION_SUCCEEDED || resolver.getEventType() == EventType.TRANSACTION_FAILED ) {
        resolver.transaction = PaymillContext.PARSER.readValue( eventResource.toString(), Transaction.class );
      }
      if( resolver.getEventType() == EventType.SUBSCRIPTION_CREATED || resolver.getEventType() == EventType.SUBSCRIPTION_UPDATED
          || resolver.getEventType() == EventType.SUBSCRIPTION_DELETED || resolver.getEventType() == EventType.SUBSCRIPTION_EXPIRING
          || resolver.getEventType() == EventType.SUBSCRIPTION_DEACTIVATED || resolver.getEventType() == EventType.SUBSCRIPTION_ACTIVATED
          || resolver.getEventType() == EventType.SUBSCRIPTION_CANCELED ) {
        resolver.subscription = PaymillContext.PARSER.readValue( eventResource.toString(), Subscription.class );
      }
      if( resolver.getEventType() == EventType.REFUND_CREATED || resolver.getEventType() == EventType.REFUND_SUCCEEDED
          || resolver.getEventType() == EventType.REFUND_FAILED ) {
        resolver.refund = PaymillContext.PARSER.readValue( eventResource.toString(), Refund.class );
      }
      if( resolver.getEventType() == EventType.CLIENT_UPDATED ) {
        resolver.client = PaymillContext.PARSER.readValue( eventResource.toString(), Client.class );
      }
      if( resolver.getEventType() == EventType.SUBSCRIPTION_SUCCEEDED || resolver.getEventType() == EventType.SUBSCRIPTION_FAILED ) {
        resolver.subscription = PaymillContext.PARSER.readValue( eventResource.get( "subscription" ).toString(), Subscription.class );
        resolver.transaction = PaymillContext.PARSER.readValue( eventResource.get( "transaction" ).toString(), Transaction.class );
      }
      if( resolver.getEventType() == EventType.PAYMENT_EXPIRED ) {
        resolver.payment = PaymillContext.PARSER.readValue( eventResource.toString(), Payment.class );
      }
      if( resolver.getEventType() == EventType.PAYOUT_TRANSFERRED || resolver.getEventType() == EventType.INVOICE_AVAILABLE ) {
        resolver.invoice = PaymillContext.PARSER.readValue( eventResource.toString(), Invoice.class );
      }
      if( resolver.getEventType() == EventType.APP_MERCHANT_ACTIVATED || resolver.getEventType() == EventType.APP_MERCHANT_DEACTIVATED
          || resolver.getEventType() == EventType.APP_MERCHANT_REJECTED || resolver.getEventType() == EventType.APP_MERCHANT_LIVE_REQUESTS_ALLOWED
          || resolver.getEventType() == EventType.APP_MERCHANT_LIVE_REQUESTS_NOT_ALLOWED || resolver.getEventType() == EventType.APP_MERCHANT_APP_DISABLED ) {
        resolver.merchant = PaymillContext.PARSER.readValue( eventResource.toString(), Merchant.class );
      }
      if( eventNode.has( "created_at" ) ) {
        resolver.createdAt = new Date( Long.parseLong( eventNode.get( "created_at" ).toString() ) * 1000 );
      }
      if( eventNode.has( "app_id" ) ) {
        resolver.appId = PaymillContext.PARSER.readValue( eventNode.get( "app_id" ).toString(), String.class );
        ;
      }
      return resolver;
    } catch( Exception e ) {
      throw new RuntimeException( e );
    }
  }
}
