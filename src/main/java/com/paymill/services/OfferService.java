package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Offer;
import com.paymill.utils.RestfulUtils;
import com.paymill.utils.ValidationUtils;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class OfferService implements PaymillService {

  private final static String PATH = "/offers";

  public Offer show( Offer offer ) {
    return RestfulUtils.show( OfferService.PATH, offer, Offer.class );
  }

  public Offer create( Integer amount, String currency, String interval, String name ) {
    return this.create( amount, currency, interval, name, null );
  }

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

  public Offer update( Offer offer ) {
    return RestfulUtils.update( OfferService.PATH, offer, Offer.class );
  }

  public Offer delete( Offer offer ) {
    return RestfulUtils.delete( OfferService.PATH, offer, Offer.class );
  }

}
