package com.paymill.services;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.Paymill;
import com.paymill.models.Client;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
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
  private Offer               offer;

  @BeforeClass
  public void setUp() {
    Paymill.setApiKey( "255de920504bd07dad2a0bf57822ee40" );

    this.subscriptionService = Paymill.getService( SubscriptionService.class );
    this.paymentService = Paymill.getService( PaymentService.class );
    this.clientService = Paymill.getService( ClientService.class );
    this.offerService = Paymill.getService( OfferService.class );

    this.client = this.clientService.create( this.clientEmail, this.clientDescription );
    this.payment = this.paymentService.createWithTokenAndClient( this.token, this.client.getId() );
    this.offer = this.offerService.create( this.amount, this.currency, this.interval, this.name );
  }

  @AfterClass
  public void tearDown() {
    List<Subscription> subscriptions = this.subscriptionService.list();
    for( Subscription subscription : subscriptions ) {
      System.out.println( this.subscriptionService.delete( subscription ) );
    }
    this.clientService.delete( client );
  }

  @Test
  public void test() {
    Subscription subscription = this.subscriptionService.create( offer, payment );
    Assert.assertNotNull( subscription );
  }
}
