package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Client;
import com.paymill.models.Offer;
import com.paymill.models.PaymillList;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The {@link OfferService} is used to list, create, edit, delete and update PAYMILL {@link Offer}s.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public class OfferService extends AbstractService {

  private OfferService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  private final static String PATH = "/offers";

  /**
   * This function returns a {@link List} of PAYMILL {@link Offer} objects.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Offer}s and their total count.
   */
  public PaymillList<Offer> list() {
    return this.list( null, null, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Offer} objects, overriding the default count and offset.
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Offer}s and their total count.
   */
  public PaymillList<Offer> list( Integer count, Integer offset ) {
    return this.list( null, null, count, offset );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Offer} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link Offer.Filter} or <code>null</code>
   * @param order
   *          {@link Offer.Order} or <code>null</code>
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Offer}s and their total count.
   */
  public PaymillList<Offer> list( Offer.Filter filter, Offer.Order order ) {
    return this.list( filter, order, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Offer} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied, overriding the default count and
   * offset.
   * @param filter
   *          {@link Offer.Filter} or <code>null</code>
   * @param order
   *          {@link Offer.Order} or <code>null</code>
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Offer}s and their total count.
   */
  public PaymillList<Offer> list( Offer.Filter filter, Offer.Order order, Integer count, Integer offset ) {
    return RestfulUtils.list( OfferService.PATH, filter, order, count, offset, Offer.class, super.httpClient );
  }

  /**
   * Get and refresh the details of an existing PAYMILL {@link Offer}.
   * @param offer
   *          A {@link Offer} with Id.
   * @return Refreshed instance of the given {@link Offer}.
   */
  public Offer get( Offer offer ) {
    return RestfulUtils.show( OfferService.PATH, offer, Offer.class, super.httpClient );
  }

  /**
   * Get and refresh the details of an existing PAYMILL {@link Offer}.
   * @param offerId
   *          Id of the {@link Offer}
   * @return Refreshed instance of the given {@link Offer}.
   */
  public Offer get( String offerId ) {
    return this.get( new Offer( offerId ) );
  }

  /**
   * Creates an offer via the API.
   * @param amount
   *          Amount in cents > 0.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param interval
   *          Defining how often the {@link Client} should be charged. Format: number DAY | WEEK | MONTH | YEAR
   * @param name
   *          Your name for this offer
   * @return {@link Offer} object with id, which represents a PAYMILL offer.
   */
  public Offer create( Integer amount, String currency, String interval, String name ) {
    return this.create( amount, currency, interval, name, null );
  }

  /**
   * Creates an offer via the API.
   * @param amount
   *          Amount in cents > 0.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param interval
   *          Defining how often the {@link Client} should be charged. Format: number DAY | WEEK | MONTH | YEAR
   * @param name
   *          Your name for this offer
   * @param trialPeriodDays
   *          Give it a try or charge directly. Can be <code>null</code>.
   * @return {@link Offer} object with id, which represents a PAYMILL offer.
   */
  public Offer create( Integer amount, String currency, String interval, String name, Integer trialPeriodDays ) {
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );
    ValidationUtils.validatesInterval( interval );
    ValidationUtils.validatesName( name );
    ValidationUtils.validatesTrialPeriodDays( trialPeriodDays );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );
    params.add( "interval", interval );
    params.add( "name", name );
    if( trialPeriodDays != null )
      params.add( "trial_period_days", String.valueOf( trialPeriodDays ) );

    return RestfulUtils.create( OfferService.PATH, params, Offer.class, super.httpClient );
  }

  /**
   * Updates the offer. Only the name can be changed all other attributes cannot be edited.
   * @param offer
   *          {@link Offer} with Id.
   */
  public void update( Offer offer ) {
    RestfulUtils.update( OfferService.PATH, offer, Offer.class, super.httpClient );
  }

  /**
   * An {@link Offer} can be deleted if no {@link Client} is subscribed to it.
   * @param offer
   *          {@link Offer} with id to be deleted.
   */
  public void delete( Offer offer ) {
    RestfulUtils.delete( OfferService.PATH, offer, Offer.class, super.httpClient );
  }

  /**
   * An {@link Offer} can be deleted if no {@link Client} is subscribed to it.
   * @param offerId
   *          Id of the {@link Offer}.
   */
  public void delete( String offerId ) {
    ValidationUtils.validatesId( offerId );
    this.delete( new Offer( offerId ) );
  }

}
