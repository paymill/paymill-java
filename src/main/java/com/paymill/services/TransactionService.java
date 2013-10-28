package com.paymill.services;

import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.Paymill;
import com.paymill.models.Fee;
import com.paymill.models.Transaction;
import com.paymill.utils.ValidationUtils;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class TransactionService extends BaseService {

  private static String RESOURCE = "/transactions";

  public Transaction createWithToken( String token, Integer amount, String currency ) {
    return this.createWithToken( token, amount, currency, null, null, null );
  }

  public Transaction createWithToken( String token, Integer amount, String currency, Fee fee ) {
    return this.createWithToken( token, amount, currency, null, fee.getAmount(), fee.getPayment() );
  }

  public Transaction createWithToken( String token, Integer amount, String currency, String description ) {
    return this.createWithToken( token, amount, currency, description, null, null );
  }

  public Transaction createWithToken( String token, Integer amount, String currency, String description, Fee fee ) {
    return this.createWithToken( token, amount, currency, description, fee.getAmount(), fee.getPayment() );
  }

  public Transaction createWithToken( String token, Integer amount, String currency, Integer feeAmount, String feePayment ) {
    return this.createWithToken( token, amount, currency, null, feeAmount, feePayment );
  }

  public Transaction createWithToken( String token, Integer amount, String currency, String description, Integer feeAmount, String feePayment ) {
    ValidationUtils.validatesToken( token );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );
    ValidationUtils.validatesFee( feeAmount, feePayment );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "token", token );
    params.putAll( this.fillMap( amount, currency, description, feeAmount, feePayment ) );

    return super.post( params );
  }

  public void list( Integer count, Integer offset, Date created_at ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + RESOURCE );
    ClientResponse response = webResource.get( ClientResponse.class );

    if( response.getStatus() != 200 ) {
      System.out.println( "Failed : HTTP error code : " + response.getStatus() );
    }

    String output = response.getEntity( String.class );

  }

  private MultivaluedMap<String, String> fillMap( Integer amount, String currency, String description, Integer feeAmount, String feePayment ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();

    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );
    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );
    if( feeAmount != null )
      params.add( "fee_amount", String.valueOf( feeAmount ) );
    if( StringUtils.isNotBlank( feePayment ) )
      params.add( "fee_payment", feePayment );

    return params;
  }

  public Transaction createWithPayment() {
    return null;
  }

  public Transaction createWithPreauthorization() {
    return null;
  }

  @Override
  protected String getPath() {
    return TransactionService.RESOURCE;
  }

  @Override
  protected Class<?> getClazz() {
    return Transaction.class;
  }

}
