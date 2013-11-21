package com.paymill.services;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Interval;
import com.paymill.models.Offer;
import com.paymill.models.PaymillList;

public class OfferServiceTest {

  private Integer      amount          = 900;
  private String       currency        = "EUR";
  private String       interval        = "1 MONTH";
  private String       name            = "Chuck Testa";
  private Integer      trialPeriodDays = 14;

  private OfferService offerService;

  private List<Offer>  offers          = new ArrayList<Offer>();
  private Offer        offerWithTrial;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( "255de920504bd07dad2a0bf57822ee40" );
    this.offerService = paymill.getOfferService();
  }

  @AfterClass
  public void tearDown() {
    List<Offer> offers = this.offerService.list().getData();
    for( Offer offer : offers ) {
      Assert.assertNull( this.offerService.delete( offer ).getId() );
    }
    Assert.assertEquals( this.offerService.list().getData().size(), 0 );
  }

  @Test
  public void testCreate_WithoutTrial_shouldSecceed() {
    Offer offer = this.offerService.create( amount, currency, interval, name );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getAmount(), this.amount );
    Assert.assertEquals( offer.getTrialPeriodDays(), Integer.valueOf( 0 ) );
    this.offers.add( offer );
  }

  @Test
  public void testCreate_WithTrial_shouldSecceed() {
    Offer offer = this.offerService.create( amount + 1, currency, interval, name, trialPeriodDays );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getAmount(), Integer.valueOf( this.amount + 1 ) );
    Assert.assertEquals( offer.getName(), this.name );
    Assert.assertEquals( offer.getTrialPeriodDays(), this.trialPeriodDays );
    this.offerWithTrial = offer;
    this.offers.add( offer );
  }

  @Test( dependsOnMethods = "testCreate_WithTrial_shouldSecceed" )
  public void testShow_shouldSecceed() {
    Offer offer = this.offerService.show( this.offerWithTrial );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getName(), this.name );
    Assert.assertEquals( offer.getTrialPeriodDays(), this.trialPeriodDays );
  }

  @Test( dependsOnMethods = "testShow_shouldSecceed" )
  public void testUpdate_shouldSucceed() {
    this.offerWithTrial.setName( "Charles A. 'Chuck' Testa" );
    Offer offer = this.offerService.update( this.offerWithTrial );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getName(), "Charles A. 'Chuck' Testa" );

    //TODO[VNi]: after update api returns 0 days trial period
    //    Assert.assertEquals( offer.getTrialPeriodDays(), this.trialPeriodDays );
  }

  @Test( dependsOnMethods = "testUpdate_shouldSucceed" )
  public void testListOrderByAmountDesc() {
    Offer.Order order = Offer.createOrder().byAmount().desc();

    PaymillList<Offer> wrapper = this.offerService.list( null, order );
    List<Offer> offers = wrapper.getData();

    Assert.assertNotNull( offers );
    Assert.assertFalse( offers.isEmpty() );
    Assert.assertEquals( this.offers.size(), offers.size() );
    Assert.assertEquals( offers.get( 0 ).getAmount(), Integer.valueOf( this.amount + 1 ) );
    Assert.assertEquals( offers.get( 1 ).getAmount(), this.amount );
  }

  @Test( dependsOnMethods = "testListOrderByAmountDesc" )
  public void testListFilterByAmount() {
    Offer.Filter filter = Offer.createFilter().byAmount( this.amount );

    PaymillList<Offer> wrapper = this.offerService.list( filter, null );
    List<Offer> offers = wrapper.getData();

    Assert.assertNotNull( offers );
    Assert.assertFalse( offers.isEmpty() );
    Assert.assertEquals( this.offers.size() - 1, offers.size() );

    Assert.assertEquals( offers.get( 0 ).getAmount(), this.amount );
  }

  private void validatesOffer( Offer offer ) {
    Assert.assertNotNull( offer );
    Assert.assertNotNull( offer.getId() );
    Assert.assertEquals( offer.getCurrency(), this.currency );
    Assert.assertNotNull( offer.getInterval() );
    Assert.assertEquals( offer.getInterval().getInterval(), Integer.valueOf( 1 ) );
    Assert.assertEquals( offer.getInterval().getUnit(), Interval.Unit.MONTH );
    Assert.assertNotNull( offer.getCreatedAt() );
    Assert.assertNotNull( offer.getUpdatedAt() );
    Assert.assertNull( offer.getAppId() );
    //    "subscription_count": {
    //        "active": "3",
    //        "inactive": 0
    //    },
  }

}
