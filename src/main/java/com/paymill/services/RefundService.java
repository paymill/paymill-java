package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.PaymillList;
import com.paymill.models.Refund;
import com.paymill.models.Transaction;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The {@link RefundService} is used to list and create PAYMILL {@link Refund}s.
 * @author Vassil Nikolov
 */
public class RefundService extends AbstractService {

  private final static String PATH = "/refunds";

  private RefundService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Refund} objects.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Refund}s and their total count.
   */
  public PaymillList<Refund> list() {
    return this.list( null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Refund} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link Refund.Filter} or <code>null</code>
   * @param order
   *          {@link Refund.Order} or <code>null</code>
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Refund}s and their total count.
   */
  public PaymillList<Refund> list( Refund.Filter filter, Refund.Order order ) {
    return RestfulUtils.list( RefundService.PATH, filter, order, Refund.class, super.httpClient );
  }

  /**
   * Returns detailed informations of a specific {@link Refund}.
   * @param refund
   *          A {@link Refund} with Id.
   * @return {@link Refund} object, which represents a PAYMILL refund.
   */
  public Refund get( Refund refund ) {
    return RestfulUtils.show( RefundService.PATH, refund, Refund.class, super.httpClient );
  }

  /**
   * Returns detailed informations of a specific {@link Refund}.
   * @param refundId
   *          Id of the {@link Refund}
   * @return {@link Refund} object, which represents a PAYMILL refund.
   */
  public Refund get( String refundId ) {
    return this.get( new Refund( refundId ) );
  }

  /**
   * This function refunds a {@link Transaction} that has been created previously and was refunded in parts or wasn’t refunded at
   * all. The inserted amount will be refunded to the credit card / direct debit of the original {@link Transaction}. There will
   * be some fees for the merchant for every refund. <br />
   * <br />
   * Note:
   * <ul>
   * <li>You can refund parts of a transaction until the transaction amount is fully refunded. But be careful there will be a fee
   * for every refund</li>
   * <li>There is no need to define a currency for refunds, because they will be in the same currency as the original transaction</li>
   * </ul>
   * @param transaction
   * @param amount
   * @return
   */
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

    return RestfulUtils.create( RefundService.PATH + "/" + transaction.getId(), params, Refund.class, super.httpClient );
  }

}
