package com.paymill.services;

import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Client;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Subscription;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class SubscriptionService extends AbstractService {

  private final static String PATH = "/subscriptions";

  private SubscriptionService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  public PaymillList<Subscription> list() {
    return this.list( null, null );
  }

  public PaymillList<Subscription> list( Subscription.Filter filter, Subscription.Order order ) {
    return RestfulUtils.list( SubscriptionService.PATH, filter, order, Subscription.class, super.httpClient );
  }

  public Subscription get( Subscription subscription ) {
    return RestfulUtils.show( SubscriptionService.PATH, subscription, Subscription.class, super.httpClient );
  }

  public Subscription get( String subscriptionId ) {
    return this.get( new Subscription( subscriptionId ) );
  }

  public Subscription createWithOfferAndPayment( Offer offer, Payment payment ) {
    ValidationUtils.validatesOffer( offer );
    ValidationUtils.validatesPayment( payment );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "offer", offer.getId() );
    params.add( "payment", payment.getId() );

    return RestfulUtils.create( SubscriptionService.PATH, params, Subscription.class, super.httpClient );
  }

  public Subscription createWithOfferAndPayment( String offerId, String paymentId ) {
    return this.createWithOfferAndPayment( new Offer( offerId ), new Payment( paymentId ) );
  }

  public Subscription createWithOfferPaymentAndClient( Offer offer, Payment payment, Client client ) {
    return this.createWithOfferPaymentAndClient( offer, payment, client, null );
  }

  public Subscription createWithOfferPaymentAndClient( String offerId, String paymentId, String clientId ) {
    return this.createWithOfferPaymentAndClient( new Offer( offerId ), new Payment( paymentId ), new Client( clientId ), null );
  }

  public Subscription createWithOfferPaymentAndClient( String offerId, String paymentId, String clientId, Date trialStart ) {
    return this.createWithOfferPaymentAndClient( new Offer( offerId ), new Payment( paymentId ), new Client( clientId ), trialStart );
  }

  public Subscription createWithOfferPaymentAndClient( Offer offer, Payment payment, Client client, Date trialStart ) {
    ValidationUtils.validatesOffer( offer );
    ValidationUtils.validatesPayment( payment );
    ValidationUtils.validatesClient( client );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "offer", offer.getId() );
    params.add( "payment", payment.getId() );
    params.add( "client", client.getId() );
    if( trialStart != null ) {
      params.add( "start_at", String.valueOf( trialStart.getTime() ) );
    }

    return RestfulUtils.create( SubscriptionService.PATH, params, Subscription.class, super.httpClient );
  }

  public void update( Subscription subscription ) {
    RestfulUtils.update( SubscriptionService.PATH, subscription, Subscription.class, super.httpClient );
  }

  public Subscription delete( Subscription subscription ) {
    return RestfulUtils.delete( SubscriptionService.PATH, subscription, Subscription.class, super.httpClient );
  }

  public Subscription delete( String subscriptionId ) {
    return this.delete( new Subscription( subscriptionId ) );
  }

}
