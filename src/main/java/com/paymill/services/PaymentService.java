package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.Paymill;
import com.paymill.models.Payment;
import com.paymill.utils.ValidationUtils;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PaymentService extends BaseService {

  private static String RESOURCE = "/payments";

  public Payment createCreditCardWithToken( String token ) {
    return this.createCreditCardWithTokenAndClient( token, null );
  }

  public Payment createCreditCardWithTokenAndClient( String token, String clientId ) {
    ValidationUtils.validatesToken( token );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "token", token );
    if( StringUtils.isNotBlank( clientId ) )
      params.add( "client", clientId );

    return super.post( params );
  }

  public Payment delete( Payment payment ) {
    return Paymill.delete( PaymentService.RESOURCE, payment );
  }

  @Override
  protected String getPath() {
    return PaymentService.RESOURCE;
  }

  @Override
  protected Class<?> getClazz() {
    return Payment.class;
  }

}
