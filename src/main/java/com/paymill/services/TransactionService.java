package com.paymill.services;

import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.Paymill;
import com.paymill.models.Fee;
import com.paymill.models.Transaction;
import com.paymill.utils.RestfulUtils;
import com.paymill.utils.ValidationUtils;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class TransactionService implements PaymillService {

  private final static String PATH = "/transactions";

  public void list( Integer count, Integer offset, Date created_at ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + PATH );
    ClientResponse response = webResource.get( ClientResponse.class );

    if( response.getStatus() != 200 ) {
      System.out.println( "Failed : HTTP error code : " + response.getStatus() );
    }

    response.getEntity( String.class );
  }

  public Transaction show( Transaction transaction ) {
    return RestfulUtils.show( TransactionService.PATH, transaction, Transaction.class );
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

    return RestfulUtils.create( TransactionService.PATH, params, Transaction.class );
  }

  public Transaction createWithPayment() {
    return null;
  }

  public Transaction createWithPreauthorization() {
    return null;
  }

  public Transaction update( Transaction transaction ) {
    return RestfulUtils.update( TransactionService.PATH, transaction, Transaction.class );
  }

}
