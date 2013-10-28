package com.paymill.utils;

import org.apache.commons.lang3.StringUtils;

public final class ValidationUtils {

  public static void validatesId( String id ) {
    if( StringUtils.isBlank( id ) )
      throw new IllegalArgumentException( "Id can not be blank" );
  }

  public static void validatesToken( String token ) {
    if( StringUtils.isBlank( token ) )
      throw new IllegalArgumentException( "Token can not be blank" );
  }

  public static void validatesAmount( Integer amount ) {
    if( amount == null || amount < 0 )
      throw new IllegalArgumentException( "Amount can not be blank or negative" );
  }

  public static void validatesCurrency( String currency ) {
    if( StringUtils.isBlank( currency ) )
      throw new IllegalArgumentException( "Currency can not be blank" );
  }

  public static void validatesFee( Integer feeAmount, String feePayment ) {
    if( feeAmount != null && StringUtils.isBlank( feePayment ) )
      throw new IllegalArgumentException( "When fee amount is given, fee payment is mandatory" );
    if( feeAmount == null && StringUtils.isNotBlank( feePayment ) )
      throw new IllegalArgumentException( "When fee payment is given, fee amount is mandatory" );

    if( feeAmount != null && StringUtils.isNotBlank( feePayment ) ) {
      if( feeAmount < 0 )
        throw new IllegalArgumentException( "Fee amount can not be negative" );
      if( !feePayment.startsWith( "pay_" ) ) {
        throw new IllegalArgumentException( "Fee payment should statrt with 'pay_' prefix" );
      }
    }
  }

}
