package com.paymill.utils;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Fee;

public final class ValidationUtils {

  public static void validatesId( String id ) {
    if( StringUtils.isBlank( id ) )
      throw new IllegalArgumentException( "Id can not be blank" );
  }

  public static void validatesToken( String token ) {
    if( StringUtils.isBlank( token ) )
      throw new IllegalArgumentException( "Token can not be blank" );
  }

  public static void validatesTrialPeriodDays( Integer trialPeriodDays ) {
    if( trialPeriodDays != null && trialPeriodDays < 0 )
      throw new IllegalArgumentException( "Trial period days can not be negative" );
  }

  public static void validatesAmount( Integer amount ) {
    if( amount == null || amount < 0 )
      throw new IllegalArgumentException( "Amount can not be blank or negative" );
  }

  public static void validatesCurrency( String currency ) {
    if( StringUtils.isBlank( currency ) )
      throw new IllegalArgumentException( "Currency can not be blank" );
  }

  public static void validatesName( String name ) {
    if( StringUtils.isBlank( name ) )
      throw new IllegalArgumentException( "Name can not be blank" );
  }

  public static void validatesInterval( String interval ) {
    if( StringUtils.isBlank( interval ) )
      throw new IllegalArgumentException( "Interval can not be blank" );
  }

  public static void validatesFee( Fee fee ) {
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

}
