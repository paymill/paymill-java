package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Payment;
import com.paymill.models.Preauthorization;
import com.paymill.models.Transaction;
import com.paymill.utils.RestfullUtils;
import com.paymill.utils.ValidationUtils;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PreauthorizationService implements PaymillService {

  private final static String PATH = "/preauthorizations";

  public Preauthorization show( Preauthorization preauthorization ) {
    return RestfullUtils.show( PreauthorizationService.PATH, preauthorization, Preauthorization.class );
  }

  public Transaction createWithToken( String token, Integer amount, String currency ) {
    ValidationUtils.validatesToken( token );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();

    params.add( "token", token );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );

    return RestfullUtils.create( PreauthorizationService.PATH, params, Transaction.class );
  }

  public Transaction createWithPayment( Payment payment, Integer amount, String currency ) {
    return null;
  }

}
