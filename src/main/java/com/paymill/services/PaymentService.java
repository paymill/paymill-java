package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Payment;
import com.paymill.utils.RestfullUtils;
import com.paymill.utils.ValidationUtils;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PaymentService implements PaymillService {

  private final static String PATH = "/payments";

  public Payment show( Payment payment ) {
    return RestfullUtils.show( PaymentService.PATH, payment, Payment.class );
  }

  public Payment createCreditCardWithToken( String token ) {
    return this.createCreditCardWithTokenAndClient( token, null );
  }

  public Payment createCreditCardWithTokenAndClient( String token, String clientId ) {
    ValidationUtils.validatesToken( token );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "token", token );
    if( StringUtils.isNotBlank( clientId ) )
      params.add( "client", clientId );

    return RestfullUtils.create( PaymentService.PATH, params, Payment.class );
  }

  public Payment delete( Payment payment ) {
    return RestfullUtils.delete( PaymentService.PATH, payment, Payment.class );
  }

}
