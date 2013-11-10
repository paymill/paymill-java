package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Client;
import com.paymill.models.Payment;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PaymentService implements PaymillService {

  private final static String PATH = "/payments";

  public List<Payment> list() {
    return this.list( null, null );
  }

  public List<Payment> list( Payment.Filter filter, Payment.Order order ) {
    return RestfulUtils.list( PaymentService.PATH, filter, order, Payment.class );
  }

  public Payment get( Payment payment ) {
    return RestfulUtils.show( PaymentService.PATH, RestfulUtils.getIdByReflection( payment ), Payment.class );
  }

  public Payment get( String paymentId ) {
    return RestfulUtils.show( PaymentService.PATH, paymentId, Payment.class );
  }

  public Payment createWithToken( String token ) {
    return this.createWithTokenAndClient( token, StringUtils.EMPTY );
  }

  public Payment createWithTokenAndClient( String token, Client client ) {
    String clientId = RestfulUtils.getIdByReflection( client );
    ValidationUtils.validatesId( clientId );
    return this.createWithTokenAndClient( token, clientId );
  }

  public Payment createWithTokenAndClient( String token, String clientId ) {
    ValidationUtils.validatesToken( token );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "token", token );
    if( StringUtils.isNotBlank( clientId ) )
      params.add( "client", clientId );

    return RestfulUtils.create( PaymentService.PATH, params, Payment.class );
  }

  public Payment delete( Payment payment ) {
    RestfulUtils.delete( PaymentService.PATH, RestfulUtils.getIdByReflection( payment ), Payment.class );
    payment.setId( null );
    return payment;

  }

  public Payment delete( String paymentId ) {
    return this.delete( new Payment( paymentId ) );
  }

}
