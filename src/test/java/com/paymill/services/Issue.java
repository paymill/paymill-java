package com.paymill.services;

import com.paymill.context.PaymillContext;
import com.paymill.models.Client;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
import com.paymill.models.Subscription;

public class Issue {

  public static void main( String... args ) {

    PaymillContext paymill = new PaymillContext( "255de920504bd07dad2a0bf57822ee40" );
    String token = "098f6bcd4621d373cade4e832627b4f6";

    ClientService clientService = paymill.getClientService();
    OfferService offerService = paymill.getOfferService();
    PaymentService paymentService = paymill.getPaymentService();
    SubscriptionService subscriptionService = paymill.getSubscriptionService();

    Client client = clientService.createWithEmail( "john.rambo@gmail.com" );
    Payment payment1 = paymentService.createWithTokenAndClient( token, client );
    Offer offer = offerService.create( 1100, "EUR", "1 WEEK", "Chuck Testa" );

    Subscription subscription = subscriptionService.createWithOfferPaymentAndClient( offer, payment1, client );
    System.out.println( subscription.getId() + " payment: " + subscription.getPayment().getId() + " CanceledAt: " + subscription.getCanceledAt() );

    Payment payment2 = paymentService.createWithTokenAndClient( token, client );

    subscription.setPayment( payment2 );
    subscription = subscriptionService.update( subscription );
    System.out.println( subscription.getId() + " payment: " + subscription.getPayment().getId() + " CanceledAt: " + subscription.getCanceledAt() );

    subscription = subscriptionService.delete( subscription );
    System.out.println( subscription.getId() + " payment: " + subscription.getPayment().getId() + " CanceledAt: " + subscription.getCanceledAt() );

  }

}
