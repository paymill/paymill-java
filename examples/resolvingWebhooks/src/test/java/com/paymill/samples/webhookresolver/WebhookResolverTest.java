package com.paymill.samples.webhookresolver;

import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Webhook.EventType;

public class WebhookResolverTest {

  @Test
  public void testResolveSubscriptionSucceeded_shouldSecceed() throws Exception {
    String content = new Scanner( this.getClass().getResourceAsStream( "/subscription.succeeded.json" ) ).useDelimiter( "\\Z" ).next();
    WebhookResolver resolver = WebhookResolver.fromString( content );
    Assert.assertEquals( resolver.getEventType(), EventType.SUBSCRIPTION_SUCCEEDED );
    Assert.assertEquals( resolver.getTransaction().getId(), "tran_59c55c0a7e7d6597f08d55e5981a" );
    Assert.assertEquals( resolver.getSubscription().getId(), "sub_1dfa3dc813004a11c9d6" );
    Assert.assertNull( resolver.getRefund() );
    Assert.assertNull( resolver.getClient() );
  }

  @Test
  public void testResolveTransactionCreated_shouldSecceed() throws Exception {
    String content = new Scanner( this.getClass().getResourceAsStream( "/transaction.created.json" ) ).useDelimiter( "\\Z" ).next();
    WebhookResolver resolver = WebhookResolver.fromString( content );
    Assert.assertEquals( resolver.getEventType(), EventType.TRANSACTION_CREATED );
    Assert.assertEquals( resolver.getTransaction().getId(), "tran_656a13cb793ac165f35556f16ce5" );
    Assert.assertNull( resolver.getSubscription() );
    Assert.assertNull( resolver.getRefund() );
    Assert.assertNull( resolver.getClient() );
    Assert.assertEquals( resolver.getAppId(), "app_1234" );
  }

  @Test
  public void testResolveInvoiceAvailable_shouldSecceed() throws Exception {
    String content = new Scanner( this.getClass().getResourceAsStream( "/invoice.available.json" ) ).useDelimiter( "\\Z" ).next();
    WebhookResolver resolver = WebhookResolver.fromString( content );
    Assert.assertEquals( resolver.getEventType(), EventType.INVOICE_AVAILABLE );
    Assert.assertEquals( resolver.getInvoice().getInvoiceNumber(), "1293724" );
    Assert.assertEquals( resolver.getInvoice().getNetto(), (Integer) 12399 );
    Assert.assertEquals( resolver.getInvoice().getBrutto(), (Integer) 14755 );
    Assert.assertEquals( resolver.getInvoice().getStatus(), "sent" );
    Assert.assertEquals( resolver.getInvoice().getVatRate(), (Integer) 19 );
    Assert.assertNull( resolver.getSubscription() );
    Assert.assertNull( resolver.getRefund() );
    Assert.assertNull( resolver.getClient() );
  }

  @Test
  public void testResolveAppMerchantActivated_shouldSecceed() throws Exception {
    String content = new Scanner( this.getClass().getResourceAsStream( "/app.merchant.activated.json" ) ).useDelimiter( "\\Z" ).next();
    PaymillContext paymillContext; 
    WebhookResolver resolver = WebhookResolver.fromString( content );
    Assert.assertEquals( resolver.getEventType(), EventType.APP_MERCHANT_ACTIVATED );
    Assert.assertEquals( resolver.getMerchant().getIdentifier(), "mer_123456789" );
    Assert.assertEquals( resolver.getMerchant().getEmail(), "mail@example.com" );
    Assert.assertEquals( resolver.getMerchant().getLocale(), "de_DE" );
    Assert.assertEquals( resolver.getMerchant().getCountry(), "DEU" );
    Assert.assertEquals( resolver.getMerchant().getMethods().get( 0 ), "visa" );
    Assert.assertEquals( resolver.getMerchant().getMethods().get( 1 ), "mastercard" );
    Assert.assertNull( resolver.getSubscription() );
    Assert.assertNull( resolver.getRefund() );
    Assert.assertNull( resolver.getClient() );
    
    switch( resolver.getEventType() ) {
      case TRANSACTION_CREATED:
        Transaction transaction = paymillContext.getTransactionService().get( resolver.getTransaction() );
        break;

      default:
        break;
    }
  }
}
