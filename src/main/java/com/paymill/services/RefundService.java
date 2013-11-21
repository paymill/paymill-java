package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.PaymillList;
import com.paymill.models.Refund;
import com.paymill.models.Transaction;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class RefundService extends AbstractService {

  private final static String PATH = "/refunds";

  private RefundService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  public PaymillList<Transaction> list() {
    return this.list( null, null );
  }

  public PaymillList<Transaction> list( Refund.Filter filter, Refund.Order order ) {
    return RestfulUtils.list( RefundService.PATH, filter, order, Refund.class, super.httpClient );
  }

  public Refund show( Refund refund ) {
    return RestfulUtils.show( RefundService.PATH, RestfulUtils.getIdByReflection( refund ), Refund.class, super.httpClient );
  }

  public Refund show( String refundId ) {
    return RestfulUtils.show( RefundService.PATH, refundId, Refund.class, super.httpClient );
  }

  public Refund refundTransaction( Transaction transaction, Integer amount ) {
    return this.refundTransaction( transaction, amount, null );
  }

  public Refund refundTransaction( String transactionId, Integer amount ) {
    return this.refundTransaction( new Transaction( transactionId ), amount, null );
  }

  public Refund refundTransaction( String transactionId, Integer amount, String description ) {
    return this.refundTransaction( new Transaction( transactionId ), amount, description );
  }

  public Refund refundTransaction( Transaction transaction, Integer amount, String description ) {
    ValidationUtils.validatesAmount( amount );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "amount", String.valueOf( amount ) );
    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( RefundService.PATH + "/" + RestfulUtils.getIdByReflection( transaction ), params, Refund.class, super.httpClient );
  }

}
