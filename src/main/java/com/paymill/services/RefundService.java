package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Refund;
import com.paymill.models.Transaction;
import com.paymill.utils.RestfulUtils;
import com.paymill.utils.ValidationUtils;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class RefundService implements PaymillService {

  private final static String PATH = "/refunds";

  public Refund show( Refund refund ) {
    return RestfulUtils.show( RefundService.PATH, refund, Refund.class );
  }

  public Refund refundTransaction( Transaction transaction, Integer amount ) {
    return this.refundTransaction( transaction, amount, null );
  }

  public Refund refundTransaction( Transaction transaction, Integer amount, String description ) {
    ValidationUtils.validatesAmount( amount );

    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "amount", String.valueOf( amount ) );
    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( RefundService.PATH + "/" + RestfulUtils.getIdByReflection( transaction ), params, Refund.class );
  }

}
