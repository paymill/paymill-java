package com.paymill.services;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Client;
import com.paymill.models.Interval;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Subscription;

public class OfferServiceTest {

  private Integer             amount          = 10000;
  private String              currency        = "EUR";
  private Interval.Period     interval        = Interval.period( 1, Interval.Unit.MONTH );
  private String              name            = "Chuck Testa";
  private Integer             trialPeriodDays = 14;

  private OfferService        offerService;
  private SubscriptionService subscriptionService;
  private Payment             payment;

  private List<Offer>         offers          = new ArrayList<Offer>();
  private Offer               offerWithTrial;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );

    this.offerService = paymill.getOfferService();
    this.subscriptionService = paymill.getSubscriptionService();
    Client client = paymill.getClientService().createWithDescription( "temp user" );
    this.payment = paymill.getPaymentService().createWithTokenAndClient( "098f6bcd4621d373cade4e832627b4f6", client );
  }

  @Test
  public void testCreate_WithoutTrial_shouldSecceed() {
    Offer offer = this.offerService.create( this.amount, this.currency, this.interval, this.name );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getAmount(), this.amount );
    Assert.assertEquals( offer.getTrialPeriodDays(), Integer.valueOf( 0 ) );
    this.offers.add( offer );
  }

  @Test
  public void testCreate_WithTrial_shouldSecceed() {
    Offer offer = this.offerService.create( this.amount + 1, this.currency, this.interval, this.name, this.trialPeriodDays );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getAmount(), Integer.valueOf( this.amount + 1 ) );
    Assert.assertEquals( offer.getName(), this.name );
    Assert.assertEquals( offer.getTrialPeriodDays(), this.trialPeriodDays );
    this.offerWithTrial = offer;
    this.offers.add( offer );
  }

  @Test( dependsOnMethods = "testCreate_WithTrial_shouldSecceed" )
  public void testShow_shouldSecceed() {
    Offer offer = this.offerService.get( this.offerWithTrial );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getName(), this.name );
    Assert.assertEquals( offer.getTrialPeriodDays(), this.trialPeriodDays );
  }

  @Test( dependsOnMethods = "testShow_shouldSecceed" )
  public void testUpdate_shouldSucceed() {
    this.offerWithTrial.setName( "Charles A. 'Chuck' Testa" );
    this.offerService.update( this.offerWithTrial, false );
    this.validatesOffer( this.offerWithTrial );

    Assert.assertEquals( this.offerWithTrial.getName(), "Charles A. 'Chuck' Testa" );
    Assert.assertEquals( this.offerWithTrial.getTrialPeriodDays(), this.trialPeriodDays );
  }

  @Test( dependsOnMethods = "testUpdate_shouldSucceed" )
  public void testListOfferByAmountDesc() {
    Offer.Order order = Offer.createOrder().byAmount().desc();

    PaymillList<Offer> wrapper = this.offerService.list( null, order );
    List<Offer> offers = wrapper.getData();

    Assert.assertNotNull( offers );
    Assert.assertFalse( offers.isEmpty() );
    Assert.assertEquals( offers.get( 0 ).getAmount(), Integer.valueOf( this.amount + 1 ) );
  }

  @Test( dependsOnMethods = "testListOfferByAmountDesc" )
  public void testListFilterByAmount() {
    Offer.Filter filter = Offer.createFilter().byAmount( this.amount );

    PaymillList<Offer> wrapper = this.offerService.list( filter, null );
    List<Offer> offers = wrapper.getData();

    Assert.assertNotNull( offers );
    Assert.assertFalse( offers.isEmpty() );
    Assert.assertEquals( offers.get( 0 ).getAmount(), this.amount );
  }

  @Test
  public void testUpdateOfferAndSubscritions() {
    Offer offer = this.offerService.create( this.amount, this.currency, "1 MONTH", "test update subs" );
    validatesOffer( offer );
    Subscription subscription = this.subscriptionService.create( Subscription.create( payment, offer ) );
    Assert.assertEquals( subscription.getOffer().getId(), offer.getId() );
    Assert.assertEquals( subscription.getInterval().getInterval(), (Integer) 1 );
    Assert.assertEquals( subscription.getInterval().getUnit(), Interval.Unit.MONTH );
    offer.setInterval( new Interval.Period( "2 WEEK" ) );
    offer = this.offerService.update( offer, true );
    Assert.assertEquals( offer.getId(), offer.getId() );
    Assert.assertEquals( offer.getInterval().getInterval(), (Integer) 2 );
    Assert.assertEquals( offer.getInterval().getUnit(), Interval.Unit.WEEK );

    // TODO[VNi]: when will be the subscription updated? The update is not immediately.
    this.subscriptionService.get( subscription );
    Assert.assertEquals( subscription.getOffer().getId(), offer.getId() );
    Assert.assertEquals( subscription.getInterval().getInterval(), (Integer) 1 );
    Assert.assertEquals( subscription.getInterval().getUnit(), Interval.Unit.MONTH );
  }

  private void validatesOffer( final Offer offer ) {
    Assert.assertNotNull( offer );
    Assert.assertNotNull( offer.getId() );
    Assert.assertEquals( offer.getCurrency(), this.currency );
    Assert.assertNotNull( offer.getInterval() );
    Assert.assertEquals( offer.getInterval().getInterval(), Integer.valueOf( 1 ) );
    Assert.assertEquals( offer.getInterval().getUnit(), Interval.Unit.MONTH );
    Assert.assertNotNull( offer.getCreatedAt() );
    Assert.assertNotNull( offer.getUpdatedAt() );
    Assert.assertNull( offer.getAppId() );
  }

}
