package com.paymill.services;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Client;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
import com.paymill.models.Subscription;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class SubscriptionService implements PaymillService {

  private final static String PATH = "/subscriptions";

  public List<Subscription> list() {
    return RestfulUtils.list( SubscriptionService.PATH, null, null, Subscription.class );
  }

  public Subscription create( Offer offer, Payment payment ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();

    params.add( "offer", offer.getId() );
    params.add( "payment", payment.getId() );

    return RestfulUtils.create( SubscriptionService.PATH, params, Subscription.class );
  }

  public Subscription create( Offer offer, Payment payment, Client client ) {
    return null;
  }

  public Subscription create( Offer offer, Payment payment, Client client, Date trialStart ) {
    return null;
  }

  public Subscription delete( Subscription subscription ) {
    return RestfulUtils.delete( SubscriptionService.PATH, subscription, Subscription.class );
  }

}
