package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Preauthorization;
import com.paymill.models.Transaction;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The {@link PreauthorizationService} is used to list, create and delete PAYMILL {@link Preauthorization}s.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public class PreauthorizationService extends AbstractService {

  private final static String PATH = "/preauthorizations";

  private PreauthorizationService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Preauthorization} objects.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Preauthorization}s and their total count.
   */
  public PaymillList<Preauthorization> list() {
    return this.list( null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Preauthorization} objects. In which order this list is returned
   * depends on the optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link Preauthorization.Filter} or <code>null</code>
   * @param order
   *          {@link Preauthorization.Order} or <code>null</code>
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Preauthorization}s and their total count.
   */
  public PaymillList<Preauthorization> list( Preauthorization.Filter filter, Preauthorization.Order order ) {
    return RestfulUtils.list( PreauthorizationService.PATH, filter, order, Preauthorization.class, super.httpClient );
  }

  /**
   * Returns and refresh data of a specific {@link Preauthorization}.
   * @param preauthorization
   *          A {@link Preauthorization} with Id.
   * @return Refreshed instance of the given {@link Preauthorization}.
   */
  public Preauthorization get( Preauthorization preauthorization ) {
    return RestfulUtils.show( PreauthorizationService.PATH, preauthorization, Preauthorization.class, super.httpClient );
  }

  /**
   * Returns and refresh data of a specific {@link Preauthorization}.
   * @param preauthorizationId
   *          Id of the {@link Preauthorization}.
   * @return {@link Preauthorization} object, which represents a PAYMILL preauthorization.
   */
  public Preauthorization get( String preauthorizationId ) {
    return this.get( new Preauthorization( preauthorizationId ) );
  }

  /**
   * Creates Use either a token or an existing payment to Authorizes the given amount with the given token.
   * @param token
   *          The identifier of a token.
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @return {@link Transaction} object with the {@link Preauthorization} as sub object.
   */
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

  /**
   * Authorizes the given amount with the given {@link Payment}.<br />
   * <strong>Works only for credit cards. Direct debit not supported.</strong>
   * @param payment
   *          The {@link Payment} itself (only creditcard-object)
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @return {@link Transaction} object with the {@link Preauthorization} as sub object.
   */
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

  /**
   * This function deletes a preauthorization.
   * @param preauthorization
   *          The {@link Preauthorization} object to be deleted.
   */
  public void delete( Preauthorization preauthorization ) {
    RestfulUtils.delete( PreauthorizationService.PATH, preauthorization, Preauthorization.class, super.httpClient );
  }

  /**
   * This function deletes a preauthorization.
   * @param preauthorizationId
   *          The Id of the {@link Preauthorization}.
   */
  public void delete( String preauthorizationId ) {
    this.delete( new Preauthorization( preauthorizationId ) );
  }

  /**
   * This function deletes a preauthorization.
   * @param transaction
   *          A {@link Transaction} which should have a {@link Preauthorization} as a sub object.
   */
  public void delete( Transaction transaction ) {
    this.delete( transaction.getPreauthorization() );
  }

}
