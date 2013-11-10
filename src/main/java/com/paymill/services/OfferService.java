package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Client;
import com.paymill.models.Offer;
import com.paymill.models.Offer.Filter;
import com.paymill.models.Offer.Order;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The {@link OfferService} is used to list, create, edit, delete and update PayMill {@link Offer}s.
 * @author Vassil Nikolov
 */
public class OfferService implements PaymillService {

  private final static String PATH = "/offers";

  /**
   * This function returns a {@link List} of PayMill {@link Offer} objects.
   * @return {@link List} of PayMill {@link Offer} objects.
   */
  public List<Offer> list() {
    return this.list( null, null );
  }

  /**
   * This function returns a {@link List} of PayMill {@link Offer} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link Filter} or <code>null</code>
   * @param order
   *          {@link Order} or <code>null</code>
   * @return {@link List} of PayMill {@link Offer} objects
   */
  public List<Offer> list( Offer.Filter filter, Offer.Order order ) {
    return RestfulUtils.list( OfferService.PATH, filter, order, Offer.class );
  }

  /**
   * Get the details of an existing PayMill {@link Offer}.
   * @param offer
   *          A {@link Offer} with Id.
   * @return {@link Offer} object, which represents a PayMill offer.
   */
  public Offer show( Offer offer ) {
    return RestfulUtils.show( OfferService.PATH, RestfulUtils.getIdByReflection( offer ), Offer.class );
  }

  /**
   * Get the details of an existing PayMill {@link Offer}.
   * @param offerId
   *          Id of the {@link Offer}
   * @return {@link Offer} object, which represents a PayMill client.
   */
  public Offer show( String offerId ) {
    return RestfulUtils.show( OfferService.PATH, offerId, Offer.class );
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
   * @return {@link Offer} object with id, which represents a PayMill offer.
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
   * @return {@link Offer} object with id, which represents a PayMill offer.
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

    return RestfulUtils.create( OfferService.PATH, params, Offer.class );
  }

  /**
   * Updates the offer. Only the name can be changed all other attributes cannot be edited.
   * @param offer
   *          {@link Offer} with Id.
   * @return {@link Offer} object with id, which represents a PayMill offer.
   */
  public Offer update( Offer offer ) {
    return RestfulUtils.update( OfferService.PATH, offer, Offer.class );
  }

  /**
   * An {@link Offer} can be deleted if no {@link Client} is subscribed to it.
   * @param offer
   *          {@link Offer} with id to be deleted.
   * @return {@link Offer} object without id, which represents a deleted PayMill offer.
   */
  public Offer delete( Offer offer ) {
    RestfulUtils.delete( OfferService.PATH, RestfulUtils.getIdByReflection( offer ), Offer.class );
    offer.setId( null );
    return offer;
  }

  /**
   * An {@link Offer} can be deleted if no {@link Client} is subscribed to it.
   * @param offerId
   *          Id of the {@link Offer}.
   * @return {@link Offer} object without id, which represents a deleted PayMill offer.
   */
  public Offer delete( String offerId ) {
    return this.delete( new Offer( offerId ) );
  }

}
