package com.paymill.services;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Client;
import com.paymill.models.Fee;
import com.paymill.models.Interval;
import com.paymill.models.Offer;
import com.paymill.models.Payment;

final class ValidationUtils {

  static void validatesId( String id ) {
    if( StringUtils.isBlank( id ) )
      throw new IllegalArgumentException( "Id can not be blank" );
  }

  static void validatesToken( String token ) {
    if( StringUtils.isBlank( token ) )
      throw new IllegalArgumentException( "Token can not be blank" );
  }

  static void validatesTrialPeriodDays( Integer trialPeriodDays ) {
    if( trialPeriodDays != null && trialPeriodDays < 0 )
      throw new IllegalArgumentException( "Trial period days can not blank or be negative" );
  }

  static void validatesAmount( Integer amount ) {
    if( amount == null || amount < 0 )
      throw new IllegalArgumentException( "Amount can not be blank or negative" );
  }

  static void validatesCurrency( String currency ) {
    if( StringUtils.isBlank( currency ) )
      throw new IllegalArgumentException( "Currency can not be blank" );
  }

  static void validatesName( String name ) {
    if( StringUtils.isBlank( name ) )
      throw new IllegalArgumentException( "Name can not be blank" );
  }

  static void validatesUrl( String url ) {
    if( StringUtils.isBlank( url ) )
      throw new IllegalArgumentException( "Url can not be blank" );
  }

  static void validatesIntervalPeriod( Interval.Period interval ) {

    if( interval == null ) {
      throw new IllegalArgumentException( "Interval cannot be null" );
    }
    if( interval.getInterval() < 1 ) {
      throw new IllegalArgumentException( "Interval must be greater than zero" );
    }
    if( interval.getUnit() == null ) {
      throw new IllegalArgumentException( "Interval unit cannot be null" );
    }
  }

  static void validatesIntervalPeriodWithChargeDay( Interval.PeriodWithChargeDay interval ) {

    if( interval == null ) {
      throw new IllegalArgumentException( "Interval cannot be null" );
    }
    if( interval.getInterval() < 1 ) {
      throw new IllegalArgumentException( "Interval must be greater than zero" );
    }
    if( interval.getUnit() == null ) {
      throw new IllegalArgumentException( "Interval unit cannot be null" );
    }
  }

  static void validatesFee( Fee fee ) {
    if( fee != null ) {
      if( fee.getAmount() != null && StringUtils.isBlank( fee.getPayment() ) )
        throw new IllegalArgumentException( "When fee amount is given, fee payment is mandatory" );
      if( fee.getAmount() == null && StringUtils.isNotBlank( fee.getPayment() ) )
        throw new IllegalArgumentException( "When fee payment is given, fee amount is mandatory" );

      if( fee.getAmount() != null && StringUtils.isNotBlank( fee.getPayment() ) ) {
        if( fee.getAmount() < 0 )
          throw new IllegalArgumentException( "Fee amount can not be negative" );
        if( !fee.getPayment().startsWith( "pay_" ) ) {
          throw new IllegalArgumentException( "Fee payment should statrt with 'pay_' prefix" );
        }
      }
    }
  }

  static void validatesPayment( Payment payment ) {
    if( payment == null || StringUtils.isBlank( payment.getId() ) )
      throw new IllegalArgumentException( "Payment or its Id can not be blank" );
  }

  static void validatesOffer( Offer offer ) {
    if( offer == null || StringUtils.isBlank( offer.getId() ) )
      throw new IllegalArgumentException( "Offer or its  Id can not be blank" );
  }

  static void validatesClient( Client client ) {
    if( client == null || StringUtils.isBlank( client.getId() ) )
      throw new IllegalArgumentException( "Client or its  Id can not be blank" );
  }

}
