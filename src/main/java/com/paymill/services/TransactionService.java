package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Client;
import com.paymill.models.Fee;
import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Preauthorization;
import com.paymill.models.Transaction;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class TransactionService extends AbstractService {

  private final static String PATH = "/transactions";

  private TransactionService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  public PaymillList<Transaction> list() {
    return this.list( null, null );
  }

  public PaymillList<Transaction> list( Transaction.Filter filter, Transaction.Order order ) {
    return RestfulUtils.list( TransactionService.PATH, filter, order, Transaction.class, super.httpClient );
  }

  public Transaction get( Transaction transaction ) {
    return RestfulUtils.show( TransactionService.PATH, RestfulUtils.getIdByReflection( transaction ), Transaction.class, super.httpClient );
  }

  public Transaction get( String transactionId ) {
    return RestfulUtils.show( TransactionService.PATH, transactionId, Transaction.class, super.httpClient );
  }

  public Transaction createWithToken( String token, Integer amount, String currency ) {
    return this.createWithTokenAndFee( token, amount, currency, null, null );
  }

  public Transaction createWithToken( String token, Integer amount, String currency, String description ) {
    return this.createWithTokenAndFee( token, amount, currency, description, null );
  }

  public Transaction createWithTokenAndFee( String token, Integer amount, String currency, Fee fee ) {
    return this.createWithTokenAndFee( token, amount, currency, null, fee );
  }

  public Transaction createWithTokenAndFee( String token, Integer amount, String currency, String description, Fee fee ) {
    ValidationUtils.validatesToken( token );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );
    ValidationUtils.validatesFee( fee );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "token", token );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );

    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );
    if( fee != null && fee.getAmount() != null )
      params.add( "fee_amount", String.valueOf( fee.getAmount() ) );
    if( fee != null && StringUtils.isNotBlank( fee.getPayment() ) )
      params.add( "fee_payment", fee.getPayment() );

    return RestfulUtils.create( TransactionService.PATH, params, Transaction.class, super.httpClient );
  }

  public Transaction createWithPayment( Payment payment, Integer amount, String currency ) {
    return this.createWithPayment( payment, amount, currency, null );
  }

  public Transaction createWithPayment( String paymentId, Integer amount, String currency ) {
    return this.createWithPayment( new Payment( paymentId ), amount, currency, null );
  }

  public Transaction createWithPayment( Payment payment, Integer amount, String currency, String description ) {
    ValidationUtils.validatesPayment( payment );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "payment", payment.getId() );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );

    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( TransactionService.PATH, params, Transaction.class, super.httpClient );
  }

  public Transaction createWithPayment( String paymentId, Integer amount, String currency, String description ) {
    return this.createWithPayment( new Payment( paymentId ), amount, currency, description );
  }

  public Transaction createWithPaymentAndClient( Payment payment, Client client, Integer amount, String currency ) {
    return this.createWithPaymentAndClient( payment, client, amount, currency, null );
  }

  public Transaction createWithPaymentAndClient( String paymentId, String clientId, Integer amount, String currency ) {
    return this.createWithPaymentAndClient( paymentId, clientId, amount, currency, null );
  }

  public Transaction createWithPaymentAndClient( Payment payment, Client client, Integer amount, String currency, String description ) {
    ValidationUtils.validatesPayment( payment );
    ValidationUtils.validatesClient( client );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "payment", payment.getId() );
    params.add( "client", client.getId() );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );

    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( TransactionService.PATH, params, Transaction.class, super.httpClient );
  }

  public Transaction createWithPaymentAndClient( String paymentId, String clientId, Integer amount, String currency, String description ) {
    return this.createWithPaymentAndClient( new Payment( paymentId ), new Client( clientId ), amount, currency );
  }

  public Transaction createWithPreauthorization( Preauthorization preauthorization, Integer amount, String currency ) {
    return this.createWithPreauthorization( preauthorization.getId(), amount, currency, null );
  }

  public Transaction createWithPreauthorization( String preauthorizationId, Integer amount, String currency ) {
    return this.createWithPreauthorization( preauthorizationId, amount, currency, null );
  }

  public Transaction createWithPreauthorization( Preauthorization preauthorization, Integer amount, String currency, String description ) {
    return this.createWithPreauthorization( preauthorization.getId(), amount, currency, description );
  }

  public Transaction createWithPreauthorization( String preauthorizationId, Integer amount, String currency, String description ) {
    ValidationUtils.validatesId( preauthorizationId );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "preauthorization", preauthorizationId );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );

    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( TransactionService.PATH, params, Transaction.class, super.httpClient );
  }

  public Transaction update( Transaction transaction ) {
    return RestfulUtils.update( TransactionService.PATH, transaction, Transaction.class, super.httpClient );
  }

}
