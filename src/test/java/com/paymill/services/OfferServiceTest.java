package com.paymill.services;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.Paymill;
import com.paymill.models.Interval;
import com.paymill.models.Offer;

public class OfferServiceTest {

  private Integer      amount          = 900;
  private String       currency        = "EUR";
  private String       interval        = "1 MONTH";
  private String       name            = "Chuck Testa";
  private Integer      trialPeriodDays = 14;

  private OfferService offerService;
  private Offer        offer;

  @BeforeClass
  public void setUp() {
    Paymill.setApiKey( "2bb9c4c3f0776ba75cfdc60020d7ea35" );
    this.offerService = Paymill.getService( OfferService.class );
  }

  @Test
  public void testCreate_WithoutTrial_shouldSecceed() {
    Offer offer = this.offerService.create( amount, currency, interval, name );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getTrialPeriodDays(), Integer.valueOf( 0 ) );
  }

  @Test
  public void testCreate_WithTrial_shouldSecceed() {
    this.offer = this.offerService.create( amount, currency, interval, name, trialPeriodDays );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getName(), this.name );
    Assert.assertEquals( offer.getTrialPeriodDays(), this.trialPeriodDays );
  }

  @Test( dependsOnMethods = "testCreate_WithTrial_shouldSecceed" )
  public void testShow_shouldSecceed() {
    Offer offer = this.offerService.show( this.offer );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getName(), this.name );
    Assert.assertEquals( offer.getTrialPeriodDays(), this.trialPeriodDays );
  }

  @Test( dependsOnMethods = "testShow_shouldSecceed" )
  public void testUpdate_shouldSucceed() {
    this.offer.setName( "Charles A. 'Chuck' Testa" );
    Offer offer = this.offerService.update( this.offer );
    this.validatesOffer( offer );
    Assert.assertEquals( offer.getName(), "Charles A. 'Chuck' Testa" );
    //TODO[VNi]: after update api returns 0 days trial period
    //Assert.assertEquals( offer.getTrialPeriodDays(), this.trialPeriodDays );
  }

  public void testDelete_shouldSecceed() {
    this.offer = this.offerService.delete( this.offer );
    Assert.assertNull( this.offer );
  }


  private void validatesOffer( Offer offer ) {
    Assert.assertNotNull( offer );
    Assert.assertNotNull( offer.getId() );
    Assert.assertEquals( offer.getAmount(), this.amount );
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
