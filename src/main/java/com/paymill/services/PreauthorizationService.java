package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Preauthorization;
import com.paymill.models.Transaction;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PreauthorizationService extends AbstractService {

  private final static String PATH = "/preauthorizations";

  private PreauthorizationService( Client httpClient ) {
    super( httpClient );
  }

  public PaymillList<Preauthorization> list() {
    return this.list( null, null );
  }

  public PaymillList<Preauthorization> list( Preauthorization.Filter filter, Preauthorization.Order order ) {
    return RestfulUtils.list( PreauthorizationService.PATH, filter, order, Preauthorization.class, super.httpClient );
  }

  public Preauthorization get( Preauthorization preauthorization ) {
    return RestfulUtils.show( PreauthorizationService.PATH, RestfulUtils.getIdByReflection( preauthorization ), Preauthorization.class, super.httpClient );
  }

  public Preauthorization get( String preauthorizationId ) {
    return RestfulUtils.show( PreauthorizationService.PATH, preauthorizationId, Preauthorization.class, super.httpClient );
  }

  public Transaction createWithToken( String token, Integer amount, String currency ) {
    ValidationUtils.validatesToken( token );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();

    params.add( "token", token );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );

    return RestfulUtils.create( PreauthorizationService.PATH, params, Transaction.class, super.httpClient );
  }

  public Transaction createWithPayment( Payment payment, Integer amount, String currency ) {
    ValidationUtils.validatesPayment( payment );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();

    params.add( "payment", payment.getId() );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );

    return RestfulUtils.create( PreauthorizationService.PATH, params, Transaction.class, super.httpClient );
  }

  public Preauthorization delete( Preauthorization preauthorization ) {
    RestfulUtils.delete( PreauthorizationService.PATH, RestfulUtils.getIdByReflection( preauthorization ), Preauthorization.class, super.httpClient );
    preauthorization.setId( null );
    return preauthorization;

  }

  public Preauthorization delete( String preauthorizationId ) {
    return this.delete( new Preauthorization( preauthorizationId ) );
  }

  public Preauthorization delete( Transaction transaction ) {
    return this.delete( transaction.getPreauthorization() );
  }

}
