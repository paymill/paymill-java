package com.paymill.services;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Client;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Subscription;
import com.paymill.models.Transaction;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The {@link SubscriptionService} is used to list, create, edit, delete and update PAYMILL {@link Subscription}s.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public class SubscriptionService extends AbstractService {

  private final static String PATH = "/subscriptions";

  private SubscriptionService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Subscription} objects.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Subscription}s and their total count.
   */
  public PaymillList<Subscription> list() {
    return this.list( null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Subscription} objects. In which order this list is returned depends on
   * the optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link Subscription.Filter} or <code>null</code>
   * @param order
   *          {@link Subscription.Order} or <code>null</code>
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Subscription}s and their total count.
   */
  public PaymillList<Subscription> list( Subscription.Filter filter, Subscription.Order order ) {
    return RestfulUtils.list( SubscriptionService.PATH, filter, order, Subscription.class, super.httpClient );
  }

  /**
   * This function refresh and returns the detailed information of the concrete requested {@link Subscription}.
   * @param subscription
   *          A {@link Subscription} with Id.
   * @return Refreshed instance of the given {@link Subscription}.
   */
  public Subscription get( Subscription subscription ) {
    return RestfulUtils.show( SubscriptionService.PATH, subscription, Subscription.class, super.httpClient );
  }

  /**
   * This function refresh and returns the detailed information of the concrete requested {@link Subscription}.
   * @param subscriptionId
   *          The Id of an existing {@link Subscription}.
   * @return Refreshed instance of the given {@link Subscription}.
   */
  public Subscription get( String subscriptionId ) {
    return this.get( new Subscription( subscriptionId ) );
  }

  /**
   * This function creates a {@link Subscription} between a {@link Client} and an {@link Offer}. A {@link Client} can have several
   * {@link Subscription}s to different {@link Offer}s, but only one {@link Subscription} to the same {@link Offer}. The
   * {@link Client}s is charged for each billing interval entered. <br />
   * <strong>NOTE</strong><br />
   * This call will use the {@link Client} from the {@link Payment} object.
   * @param offer
   *          An {@link Offer} to subscribe to.
   * @param payment
   *          A {@link Payment} used for charging.
   * @return {@link Subscription}, which allows you to charge recurring payments.
   */
  public Subscription createWithOfferAndPayment( Offer offer, Payment payment ) {
    ValidationUtils.validatesOffer( offer );
    ValidationUtils.validatesPayment( payment );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "offer", offer.getId() );
    params.add( "payment", payment.getId() );

    return RestfulUtils.create( SubscriptionService.PATH, params, Subscription.class, super.httpClient );
  }

  /**
   * This function creates a {@link Subscription} between a {@link Client} and an {@link Offer}. A {@link Client} can have several
   * {@link Subscription}s to different {@link Offer}s, but only one {@link Subscription} to the same {@link Offer}. The
   * {@link Client}s is charged for each billing interval entered. <br />
   * <strong>NOTE</strong><br />
   * This call will use the {@link Client} from the {@link Payment} object.
   * @param offerId
   *          The Id of an {@link Offer} to subscribe to.
   * @param paymentId
   *          The Id of a {@link Payment} used for charging.
   * @return {@link Subscription}, which allows you to charge recurring payments.
   */
  public Subscription createWithOfferAndPayment( String offerId, String paymentId ) {
    return this.createWithOfferAndPayment( new Offer( offerId ), new Payment( paymentId ) );
  }

  /**
   * This function creates a {@link Subscription} between a {@link Client} and an {@link Offer}. A {@link Client} can have several
   * {@link Subscription}s to different {@link Offer}s, but only one {@link Subscription} to the same {@link Offer}. The
   * {@link Client}s is charged for each billing interval entered. <br />
   * <strong>NOTE</strong><br />
   * If {@link Client} not provided the {@link Client} from the payment is being used.
   * @param offer
   *          An {@link Offer} to subscribe to.
   * @param payment
   *          A {@link Payment} used for charging.
   * @param client
   *          A {@link Client} to subscribe.
   * @return {@link Subscription}, which allows you to charge recurring payments.
   */
  public Subscription createWithOfferPaymentAndClient( Offer offer, Payment payment, Client client ) {
    return this.createWithOfferPaymentAndClient( offer, payment, client, null );
  }

  /**
   * This function creates a {@link Subscription} between a {@link Client} and an {@link Offer}. A {@link Client} can have several
   * {@link Subscription}s to different {@link Offer}s, but only one {@link Subscription} to the same {@link Offer}. The
   * {@link Client}s is charged for each billing interval entered. <br />
   * <strong>NOTE</strong><br />
   * If {@link Client} not provided the {@link Client} from the payment is being used.
   * @param offerId
   *          The Id of an {@link Offer} to subscribe to.
   * @param paymentId
   *          The Id of a {@link Payment} used for charging.
   * @param clientId
   *          The Id of a {@link Client} to subscribe.
   * @return {@link Subscription}, which allows you to charge recurring payments.
   */
  public Subscription createWithOfferPaymentAndClient( String offerId, String paymentId, String clientId ) {
    return this.createWithOfferPaymentAndClient( new Offer( offerId ), new Payment( paymentId ), new Client( clientId ), null );
  }

  /**
   * This function creates a {@link Subscription} between a {@link Client} and an {@link Offer}. A {@link Client} can have several
   * {@link Subscription}s to different {@link Offer}s, but only one {@link Subscription} to the same {@link Offer}. The
   * {@link Client}s is charged for each billing interval entered. <br />
   * <strong>NOTE</strong><br />
   * If {@link Client} not provided the {@link Client} from the payment is being used.
   * @param offerId
   *          The Id of an {@link Offer} to subscribe to.
   * @param paymentId
   *          The Id of a {@link Payment} used for charging.
   * @param clientId
   *          The Id of a {@link Client} to subscribe.
   * @param trialStart
   *          Date representing trial period start.
   * @return {@link Subscription}, which allows you to charge recurring payments.
   */
  public Subscription createWithOfferPaymentAndClient( String offerId, String paymentId, String clientId, Date trialStart ) {
    return this.createWithOfferPaymentAndClient( new Offer( offerId ), new Payment( paymentId ), new Client( clientId ), trialStart );
  }

  /**
   * This function creates a {@link Subscription} between a {@link Client} and an {@link Offer}. A {@link Client} can have several
   * {@link Subscription}s to different {@link Offer}s, but only one {@link Subscription} to the same {@link Offer}. The
   * {@link Client}s is charged for each billing interval entered. <br />
   * <strong>NOTE</strong><br />
   * If {@link Client} not provided the {@link Client} from the payment is being used.
   * @param offer
   *          An {@link Offer} to subscribe to.
   * @param payment
   *          A {@link Payment} used for charging.
   * @param client
   *          A {@link Client} to subscribe.
   * @param trialStart
   *          Date representing trial period start.
   * @return {@link Subscription}, which allows you to charge recurring payments.
   */
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

  /**
   * This function updates the {@link Subscription} of a {@link Client}. You can change e.g. the cancelAtPeriodEnd attribute to
   * terminate a {@link Subscription} at a special point of time. Or you can change the {@link Payment}. All other edited
   * properties will be set back to its source defined in PAYMILL.
   * @param subscription
   *          A {@link Subscription} with cancelAtPeriodEnd or {@link Payment}.
   */
  public void update( Subscription subscription ) {
    RestfulUtils.update( SubscriptionService.PATH, subscription, Subscription.class, super.httpClient );
  }

  /**
   * This function removes an existing subscription. If you set the attribute cancelAtPeriodEnd parameter to the value
   * <code>true</code>, the subscription will remain active until the end of the period. The subscription will not be renewed
   * again. If the value is set to <code>false</code> it is directly terminated, but pending {@link Transaction}s will still be
   * charged.
   * @param subscription
   *          A {@link Subscription} with Id to be deleted.
   */
  public void delete( Subscription subscription ) {
    RestfulUtils.delete( SubscriptionService.PATH, subscription, Subscription.class, super.httpClient );
  }

  /**
   * This function removes an existing subscription. If you set the attribute cancelAtPeriodEnd parameter to the value
   * <code>true</code>, the subscription will remain active until the end of the period. The subscription will not be renewed
   * again. If the value is set to <code>false</code> it is directly terminated, but pending {@link Transaction}s will still be
   * charged.
   * @param subscriptionId
   *          Id of the {@link Subscription}, which have to be deleted.
   */
  public void delete( String subscriptionId ) {
    this.delete( new Subscription( subscriptionId ) );
  }

}
