package com.paymill.services;

import com.paymill.models.PaymillList;
import com.paymill.models.Refund;
import com.paymill.models.Transaction;
import com.paymill.utils.HttpClient;
import com.paymill.utils.ParameterMap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * The {@link RefundService} is used to list and create PAYMILL {@link Refund}s.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public class RefundService extends AbstractService {

  private final static String PATH = "/refunds";

  private RefundService( HttpClient httpClient ) {
    super( httpClient );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Refund} objects.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Refund}s and their total count.
   */
  public PaymillList<Refund> list() {
    return this.list( null, null, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Refund} objects, overriding the default count and offset.
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Refund}s and their total count.
   */
  public PaymillList<Refund> list( Integer count, Integer offset ) {
    return this.list( null, null, count, offset );
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
    return this.list( filter, order, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Refund} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied, overriding the default count and
   * offset.
   * @param filter
   *          {@link Refund.Filter} or <code>null</code>
   * @param order
   *          {@link Refund.Order} or <code>null</code>
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Refund}s and their total count.
   */
  public PaymillList<Refund> list( Refund.Filter filter, Refund.Order order, Integer count, Integer offset ) {
    return RestfulUtils.list( RefundService.PATH, filter, order, count, offset, Refund.class, super.httpClient );
  }

  /**
   * Returns and refresh detailed informations of a specific {@link Refund}.
   * @param refund
   *          A {@link Refund} with Id.
   * @return Refreshed instance of the given {@link Refund}.
   */
  public Refund get( Refund refund ) {
    return RestfulUtils.show( RefundService.PATH, refund, Refund.class, super.httpClient );
  }

  /**
   * Returns and refresh detailed informations of a specific {@link Refund}.
   * @param refundId
   *          Id of the {@link Refund}
   * @return Refreshed instance of the given {@link Refund}.
   */
  public Refund get( String refundId ) {
    return this.get( new Refund( refundId ) );
  }

  /**
   * This function refunds a {@link Transaction} that has been created previously and was refunded in parts or wasn't refunded at
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
   *          The {@link Transaction}, which will be refunded.
   * @param amount
   *          Amount (in cents) which will be charged
   * @return A {@link Refund} for the given {@link Transaction}.
   */
  public Refund refundTransaction( Transaction transaction, Integer amount ) {
    return this.refundTransaction( transaction, amount, null );
  }

  /**
   * This function refunds a {@link Transaction} that has been created previously and was refunded in parts or wasn't refunded at
   * all. The inserted amount will be refunded to the credit card / direct debit of the original {@link Transaction}. There will
   * be some fees for the merchant for every refund. <br />
   * <br />
   * Note:
   * <ul>
   * <li>You can refund parts of a transaction until the transaction amount is fully refunded. But be careful there will be a fee
   * for every refund</li>
   * <li>There is no need to define a currency for refunds, because they will be in the same currency as the original transaction</li>
   * </ul>
   * @param transactionId
   *          Id of {@link Transaction}, which will be refunded.
   * @param amount
   *          Amount (in cents) which will be charged.
   * @return A {@link Refund} for the given {@link Transaction}.
   */
  public Refund refundTransaction( String transactionId, Integer amount ) {
    return this.refundTransaction( new Transaction( transactionId ), amount, null );
  }

  /**
   * This function refunds a {@link Transaction} that has been created previously and was refunded in parts or wasn't refunded at
   * all. The inserted amount will be refunded to the credit card / direct debit of the original {@link Transaction}. There will
   * be some fees for the merchant for every refund. <br />
   * <br />
   * Note:
   * <ul>
   * <li>You can refund parts of a transaction until the transaction amount is fully refunded. But be careful there will be a fee
   * for every refund</li>
   * <li>There is no need to define a currency for refunds, because they will be in the same currency as the original transaction</li>
   * </ul>
   * @param transactionId
   *          Id of {@link Transaction}, which will be refunded.
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param description
   *          Additional description for this refund.
   * @return A {@link Refund} for the given {@link Transaction}.
   */
  public Refund refundTransaction( String transactionId, Integer amount, String description ) {
    return this.refundTransaction( new Transaction( transactionId ), amount, description );
  }

  /**
   * This function refunds a {@link Transaction} that has been created previously and was refunded in parts or wasn't refunded at
   * all. The inserted amount will be refunded to the credit card / direct debit of the original {@link Transaction}. There will
   * be some fees for the merchant for every refund. <br />
   * <br />
   * Note:
   * <ul>
   * <li>You can refund parts of a transaction until the transaction amount is fully refunded. But be careful there will be a fee
   * for every refund</li>
   * <li>There is no need to define a currency for refunds, because they will be in the same currency as the original transaction</li>
   * </ul>
   * @param transactionId
   *          The {@link Transaction}, which will be refunded.
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param description
   *          Additional description for this refund.
   * @return A {@link Refund} for the given {@link Transaction}.
   */
  public Refund refundTransaction( Transaction transaction, Integer amount, String description ) {
    ValidationUtils.validatesAmount( amount );

    ParameterMap<String, String> params = new ParameterMap<String, String>();
    params.add( "amount", String.valueOf( amount ) );
    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( RefundService.PATH + "/" + transaction.getId(), params, Refund.class, super.httpClient );
  }

}
