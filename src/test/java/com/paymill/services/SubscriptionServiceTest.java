package com.paymill.services;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.exceptions.PaymillException;
import com.paymill.models.Client;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Subscription;

public class SubscriptionServiceTest {

  private SubscriptionService subscriptionService;
  private PaymentService      paymentService;
  private ClientService       clientService;
  private OfferService        offerService;

  private String              clientEmail       = "john.rambo@qaiware.com";
  private String              clientDescription = "Boom, boom, shake the room";
  private String              token             = "098f6bcd4621d373cade4e832627b4f6";
  private Integer             amount            = 900;
  private String              currency          = "EUR";
  private String              interval          = "1 MONTH";
  private String              name              = "Chuck Testa";

  private Client              client;
  private Payment             payment;
  private Offer               offer1;
  private Offer               offer2;
  private List<Subscription>  subscriptions;
  private Subscription        subscription;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( "255de920504bd07dad2a0bf57822ee40" );

    this.subscriptionService = paymill.getSubscriptionService();
    this.paymentService = paymill.getPaymentService();
    this.clientService = paymill.getClientService();
    this.offerService = paymill.getOfferService();

    this.client = this.clientService.createWithEmailAndDescription( this.clientEmail, this.clientDescription );
    this.payment = this.paymentService.createWithTokenAndClient( this.token, this.client.getId() );
    this.offer1 = this.offerService.create( this.amount, this.currency, this.interval, this.name );
    this.offer2 = this.offerService.create( this.amount, "USD", this.interval, this.name );

    this.subscriptions = new ArrayList<Subscription>();
  }

  @AfterClass
  public void tearDown() {
    for( Subscription subscription : this.subscriptions ) {
      Assert.assertNull( subscription.getCanceledAt() );
      this.subscriptionService.delete( subscription );
    }

    for( Subscription subscription : this.subscriptionService.list().getData() ) {
      Assert.assertNotNull( subscription.getCanceledAt() );
    }

    //TODO[VNi]: There is an API error, creating a payment results in 2 payments in paymill
    PaymillList<Payment> wrapper = this.paymentService.list();
    for( Payment payment : wrapper.getData() ) {
      this.paymentService.delete( payment );
    }
    this.clientService.delete( this.client );
    this.offerService.delete( this.offer1 );
    this.offerService.delete( this.offer2 );
  }

  @Test
  public void testCreateWithPayment() {
    Subscription subscription = this.subscriptionService.createWithOfferAndPayment( offer1, payment );
    Assert.assertNotNull( subscription );
    Assert.assertNotNull( subscription.getClient() );
    Assert.assertFalse( subscription.getCancelAtPeriodEnd() );

    this.subscriptions.add( subscription );
  }

  @Test( expectedExceptions = PaymillException.class )
  public void testCreateWithPaymentAndClient_shouldFail() {
    this.subscriptionService.createWithOfferPaymentAndClient( offer1, payment, client );
  }

  @Test( dependsOnMethods = "testCreateWithPayment" )
  public void testCreateWithPaymentAndClient_shouldSecceed() throws Exception {
    Thread.sleep( 1000 );

    Subscription subscription = this.subscriptionService.createWithOfferPaymentAndClient( offer2, payment, client );
    Assert.assertNotNull( subscription );
    Assert.assertNotNull( subscription.getClient() );
    Assert.assertFalse( subscription.getCancelAtPeriodEnd() );

    this.subscriptions.add( subscription );
    this.subscription = subscription;
  }

  @Test( dependsOnMethods = "testCreateWithPaymentAndClient_shouldSecceed" )
  public void testUpdate() {
    this.subscription.setCancelAtPeriodEnd( true );
    this.subscriptionService.update( subscription );

    Assert.assertTrue( this.subscription.getCancelAtPeriodEnd() );
  }

  //  @Test( dependsOnMethods = "testUpdate" )
  public void testListOrderByOffer() {
    // TODO[VNi]: There is an API error: No sorting by offer.
    Subscription.Order orderDesc = Subscription.createOrder().byOffer().desc();
    Subscription.Order orderAsc = Subscription.createOrder().byOffer().asc();

    List<Subscription> subscriptionsDesc = this.subscriptionService.list( null, orderDesc ).getData();
    Assert.assertEquals( subscriptionsDesc.size(), this.subscriptions.size() );

    List<Subscription> subscriptionsAsc = this.subscriptionService.list( null, orderAsc ).getData();
    Assert.assertEquals( subscriptionsAsc.size(), this.subscriptions.size() );

    Assert.assertEquals( subscriptionsDesc.get( 0 ).getOffer().getId(), subscriptionsAsc.get( 1 ).getOffer().getId() );
    Assert.assertEquals( subscriptionsDesc.get( 1 ).getOffer().getId(), subscriptionsAsc.get( 0 ).getOffer().getId() );
  }

  @Test( dependsOnMethods = "testUpdate" )
  public void testListOrderByCreatedAt() {
    Subscription.Order orderDesc = Subscription.createOrder().byCreatedAt().desc();
    Subscription.Order orderAsc = Subscription.createOrder().byCreatedAt().asc();

    List<Subscription> subscriptionsDesc = this.subscriptionService.list( null, orderDesc ).getData();
    Assert.assertEquals( subscriptionsDesc.size(), this.subscriptions.size() );

    List<Subscription> subscriptionsAsc = this.subscriptionService.list( null, orderAsc ).getData();
    Assert.assertEquals( subscriptionsAsc.size(), this.subscriptions.size() );

    Assert.assertEquals( subscriptionsDesc.get( 0 ).getOffer().getId(), subscriptionsAsc.get( 1 ).getOffer().getId() );
    Assert.assertEquals( subscriptionsDesc.get( 1 ).getOffer().getId(), subscriptionsAsc.get( 0 ).getOffer().getId() );
  }

}
