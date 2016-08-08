package com.paymill.services;

import java.util.List;

import com.paymill.utils.HttpClient;
import com.paymill.utils.ParameterMap;
import org.apache.commons.lang3.StringUtils;

import com.paymill.context.PaymillContext;
import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Preauthorization;
import com.paymill.models.Transaction;

/**
 * The {@link PreauthorizationService} is used to list, create and delete PAYMILL {@link Preauthorization}s.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public class PreauthorizationService extends AbstractService {

  private final static String PATH = "/preauthorizations";

  private PreauthorizationService( final HttpClient httpClient ) {
    super( httpClient );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Preauthorization} objects.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Preauthorization}s and their total count.
   */
  public PaymillList<Preauthorization> list() {
    return this.list( null, null, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Preauthorization} objects, overriding the default count and offset.
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Preauthorization}s and their total count.
   */
  public PaymillList<Preauthorization> list( final Integer count, final Integer offset ) {
    return this.list( null, null, count, offset );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Preauthorization} objects. In which order this list is returned
   * depends on the optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link com.paymill.models.Preauthorization.Filter} or <code>null</code>
   * @param order
   *          {@link com.paymill.models.Preauthorization.Order} or <code>null</code>
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Preauthorization}s and their total count.
   */
  public PaymillList<Preauthorization> list( final Preauthorization.Filter filter, final Preauthorization.Order order ) {
    return this.list( filter, order, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Preauthorization} objects. In which order this list is returned
   * depends on the optional parameters. If <code>null</code> is given, no filter or order will be applied, overriding the default
   * count and offset.
   * @param filter
   *          {@link com.paymill.models.Preauthorization.Filter} or <code>null</code>
   * @param order
   *          {@link com.paymill.models.Preauthorization.Order} or <code>null</code>
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Preauthorization}s and their total count.
   */
  public PaymillList<Preauthorization> list( final Preauthorization.Filter filter, final Preauthorization.Order order, final Integer count, final Integer offset ) {
    return RestfulUtils.list( PreauthorizationService.PATH, filter, order, count, offset, Preauthorization.class, super.httpClient );
  }

  /**
   * Returns and refresh data of a specific {@link Preauthorization}.
   * @param preauthorization
   *          A {@link Preauthorization} with Id.
   * @return Refreshed instance of the given {@link Preauthorization}.
   */
  public Preauthorization get( final Preauthorization preauthorization ) {
    return RestfulUtils.show( PreauthorizationService.PATH, preauthorization, Preauthorization.class, super.httpClient );
  }

  /**
   * Returns and refresh data of a specific {@link Preauthorization}.
   * @param preauthorizationId
   *          Id of the {@link Preauthorization}.
   * @return {@link Preauthorization} object, which represents a PAYMILL preauthorization.
   */
  public Preauthorization get( final String preauthorizationId ) {
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
  public Preauthorization createWithToken( final String token, final Integer amount, final String currency ) {
    return this.createWithToken( token, amount, currency, null );
  }

  /**
   * Creates Use either a token or an existing payment to Authorizes the given amount with the given token.
   * @param token
   *          The identifier of a token.
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param description
   *          A short description for the preauthorization.
   * @return {@link Transaction} object with the {@link Preauthorization} as sub object.
   */
  public Preauthorization createWithToken( final String token, final Integer amount, final String currency, final String description ) {
    ValidationUtils.validatesToken( token );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );

    ParameterMap<String, String> params = new ParameterMap<String, String>();

    params.add( "token", token );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );
    params.add( "source", String.format( "%s-%s", PaymillContext.getProjectName(), PaymillContext.getProjectVersion() ) );

    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( PreauthorizationService.PATH, params, Preauthorization.class, super.httpClient );
  }

  /**
   * Authorizes the given amount with the given {@link Payment}.
   *
   * <strong>Works only for credit cards. Direct debit not supported.</strong>
   * @param payment
   *          The {@link Payment} itself (only creditcard-object)
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @return {@link Transaction} object with the {@link Preauthorization} as sub object.
   */
  public Preauthorization createWithPayment( final Payment payment, final Integer amount, final String currency ) {
    return this.createWithPayment( payment, amount, currency, null );
  }

  /**
   * Authorizes the given amount with the given {@link Payment}.
   *
   * <strong>Works only for credit cards. Direct debit not supported.</strong>
   * @param payment
   *          The {@link Payment} itself (only creditcard-object)
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param description
   *          A short description for the preauthorization.
   * @return {@link Transaction} object with the {@link Preauthorization} as sub object.
   */
  public Preauthorization createWithPayment( final Payment payment, final Integer amount, final String currency, final String description ) {
    ValidationUtils.validatesPayment( payment );
    ValidationUtils.validatesAmount( amount );
    ValidationUtils.validatesCurrency( currency );

    ParameterMap<String, String> params = new ParameterMap<String, String>();

    params.add( "payment", payment.getId() );
    params.add( "amount", String.valueOf( amount ) );
    params.add( "currency", currency );
    params.add( "source", String.format( "%s-%s", PaymillContext.getProjectName(), PaymillContext.getProjectVersion() ) );

    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( PreauthorizationService.PATH, params, Preauthorization.class, super.httpClient );
  }

  /**
   * This function deletes a preauthorization.
   * @param preauthorization
   *          The {@link Preauthorization} object to be deleted.
   */
  public void delete( final Preauthorization preauthorization ) {
    RestfulUtils.delete( PreauthorizationService.PATH, preauthorization, Preauthorization.class, super.httpClient );
  }

  /**
   * This function deletes a preauthorization.
   * @param preauthorizationId
   *          The Id of the {@link Preauthorization}.
   */
  public void delete( final String preauthorizationId ) {
    this.delete( new Preauthorization( preauthorizationId ) );
  }

  /**
   * This function deletes a preauthorization.
   * @param transaction
   *          A {@link Transaction} which should have a {@link Preauthorization} as a sub object.
   */
  public void delete( final Transaction transaction ) {
    this.delete( transaction.getPreauthorization() );
  }

}
