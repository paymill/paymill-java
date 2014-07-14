package com.paymill.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.exceptions.PaymillException;
import com.paymill.models.Client;
import com.paymill.models.Interval;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
import com.paymill.models.Subscription;

public class SubscriptionServiceTest {

  private final static long   TWO_WEEKS_FROM_NOW = 1000 * 60 * 60 * 24 * 14;
  private final static long   TWO_DAYS_FROM_NOW  = 1000 * 60 * 60 * 24 * 2;

  private SubscriptionService subscriptionService;
  private PaymentService      paymentService;
  private ClientService       clientService;
  private OfferService        offerService;

  private String              clientEmail        = "john.rambo@qaiware.com";
  private String              clientDescription  = "Boom, boom, shake the room";
  private String              token              = "098f6bcd4621d373cade4e832627b4f6";
  private Integer             amount             = 900;
  private String              currency           = "EUR";
  private Interval.Period     interval           = Interval.period( 1, Interval.Unit.MONTH );
  private String              name               = "Chuck Testa";

  private Client              client;
  private Payment             payment;
  private Offer               offer1;
  private Offer               offer2;
  private List<Subscription>  subscriptions;
  private Subscription        subscription;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );

    this.subscriptionService = paymill.getSubscriptionService();
    this.paymentService = paymill.getPaymentService();
    this.clientService = paymill.getClientService();
    this.offerService = paymill.getOfferService();

    this.client = this.clientService.createWithEmailAndDescription( this.clientEmail, this.clientDescription );
    this.payment = this.paymentService.createWithTokenAndClient( this.token, this.client.getId() );
    this.offer1 = this.offerService.create( this.amount, this.currency, this.interval, this.name );
    this.offer2 = this.offerService.create( this.amount * 2, this.currency, this.interval, "Updated " + this.name );

    this.subscriptions = new ArrayList<Subscription>();
  }

  @Test
  public void testCreateWithPayment() {
    this.subscription = this.subscriptionService.createWithOfferAndPayment( offer1, payment );
    Assert.assertNotNull( this.subscription );
    Assert.assertNotNull( this.subscription.getClient() );
    Assert.assertFalse( this.subscription.getCancelAtPeriodEnd() );

    this.subscriptions.add( this.subscription );
  }

  @Test
  public void testCreateWithPaymentAndClient_WithOfferWithoutTrial_shouldReturnSubscriptionWithNullTrialStartAndNullTrialEnd() {
    Client client = clientService.createWithEmail( "zendest@example.com" );
    Payment payment = paymentService.createWithTokenAndClient( "098f6bcd4621d373cade4e832627b4f6", client );
    Offer offer = offerService.create( 2223, "EUR", Interval.period( 1, Interval.Unit.WEEK ), "Offer No Trial" );

    Subscription subscriptionNoTrial = subscriptionService.createWithOfferPaymentAndClient( offer, payment, client );
    Assert.assertNull( subscriptionNoTrial.getTrialStart() );
    Assert.assertNull( subscriptionNoTrial.getTrialEnd() );
  }

  @Test
  public void testCreateWithPaymentClientAndTrial_WithOfferWithoutTrial_shouldReturnSubscriptionWithTrialEqualsTrialInSubscription() {
    Client client = clientService.createWithEmail( "zendest@example.com" );
    Payment payment = paymentService.createWithTokenAndClient( "098f6bcd4621d373cade4e832627b4f6", client );
    Offer offer = offerService.create( 2224, "EUR", Interval.period( 1, Interval.Unit.WEEK ), "Offer No Trial" );

    long trialStart = System.currentTimeMillis() + SubscriptionServiceTest.TWO_WEEKS_FROM_NOW;
    Subscription subscriptionWithTrial = subscriptionService.createWithOfferPaymentAndClient( offer, payment, client, new Date( trialStart ) );
    Assert.assertNotNull( subscriptionWithTrial.getTrialStart() );
    Assert.assertEquals( subscriptionWithTrial.getTrialEnd().getTime(), ((trialStart / 1000) * 1000) - 1000 );
  }

  @Test
  public void testCreateWithPaymentAndClient_WithOfferWithTrial_shouldReturnSubscriptionWithTrialEqualsTrialInOffer() {
    Client client = clientService.createWithEmail( "zendest@example.com" );
    Payment payment = paymentService.createWithTokenAndClient( "098f6bcd4621d373cade4e832627b4f6", client );
    Offer offer = offerService.create( 2225, "EUR", Interval.period( 1, Interval.Unit.WEEK ), "Offer With Trial", 2 );

    Subscription subscription = subscriptionService.createWithOfferPaymentAndClient( offer, payment, client );
    Assert.assertNotNull( subscription.getTrialStart() );
    Assert.assertEquals( subscription.getTrialEnd().getTime(), (subscription.getTrialStart().getTime() + SubscriptionServiceTest.TWO_DAYS_FROM_NOW) );
  }

  @Test
  public void testCreateWithPaymentClientAndTrial_WithOfferWithTrial_shouldReturnSubscriptionWithTrialEqualsTrialInSubscription() {
    Client client = clientService.createWithEmail( "zendest@example.com" );
    Payment payment = paymentService.createWithTokenAndClient( "098f6bcd4621d373cade4e832627b4f6", client );
    Offer offer = offerService.create( 2224, "EUR", Interval.period( 1, Interval.Unit.WEEK ), "Offer No Trial", 2 );

    long trialStart = System.currentTimeMillis() + SubscriptionServiceTest.TWO_WEEKS_FROM_NOW;
    Subscription subscriptionWithTrial = subscriptionService.createWithOfferPaymentAndClient( offer, payment, client, new Date( trialStart ) );
    Assert.assertNotNull( subscriptionWithTrial.getTrialStart() );
    Assert.assertEquals( subscriptionWithTrial.getTrialEnd().getTime(), ((trialStart / 1000) * 1000) - 1000 );
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
  }

  @Test( dependsOnMethods = "testCreateWithPaymentAndClient_shouldSecceed" )
  public void testUpdate() {
    String offerId = this.subscription.getOffer().getId();
    String subscriptionId = this.subscription.getId();
    Assert.assertEquals( this.subscription.getOffer().getId(), this.offer1.getId() );

    this.subscription.setCancelAtPeriodEnd( true );
    this.subscription.setOffer( this.offer2 );
    this.subscriptionService.update( this.subscription );

    Assert.assertFalse( StringUtils.equals( this.subscription.getOffer().getId(), offerId ) );
    Assert.assertEquals( this.subscription.getOffer().getId(), this.offer2.getId() );
    Assert.assertEquals( this.subscription.getId(), subscriptionId );
    Assert.assertTrue( this.subscription.getCancelAtPeriodEnd() );
  }

  //TODO[VNi]: uncomment when API returns null instead of empty array
  //@Test( dependsOnMethods = "testUpdate" )
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

  //TODO[VNi]: uncomment when API returns null instead of empty array
  //@Test( dependsOnMethods = "testUpdate" )
  public void testListOrderByCreatedAt() {
    Subscription.Order orderDesc = Subscription.createOrder().byCreatedAt().desc();
    Subscription.Order orderAsc = Subscription.createOrder().byCreatedAt().asc();

    List<Subscription> subscriptionsDesc = this.subscriptionService.list( null, orderDesc, 100000, 0 ).getData();
    for( Subscription subscription : subscriptionsDesc ) {
      if( subscription.getOffer() == null )
        this.subscriptionService.get( subscription );
    }

    List<Subscription> subscriptionsAsc = this.subscriptionService.list( null, orderAsc, 100000, 0 ).getData();

    Assert.assertEquals( subscriptionsDesc.get( 0 ).getId(), subscriptionsAsc.get( subscriptionsAsc.size() - 1 ).getId() );
    Assert.assertEquals( subscriptionsDesc.get( subscriptionsDesc.size() - 1 ).getId(), subscriptionsAsc.get( 0 ).getId() );
  }

}
