package com.paymill.context;

import java.lang.reflect.Constructor;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymill.services.ClientService;
import com.paymill.services.OfferService;
import com.paymill.services.PaymentService;
import com.paymill.services.PreauthorizationService;
import com.paymill.services.RefundService;
import com.paymill.services.SubscriptionService;
import com.paymill.services.TransactionService;
import com.paymill.services.WebhookService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * PaymillContecxt loads the context of PAYMILL for a single account, by providing a merchants private key<br />
 * It creates 8 services, which represents the PAYMILL API:
 * <ul>
 * <li>{@link ClientService}</li>
 * <li>{@link OfferService}</li>
 * <li>{@link PaymentService}</li>
 * <li>{@link PreauthorizationService}</li>
 * <li>{@link RefundService}</li>
 * <li>{@link SubscriptionService}</li>
 * <li>{@link TransactionService}</li>
 * <li>{@link WebhookService}</li>
 * </ul>
 * This services should not be created directly. They have to be obtained by the context's accessors.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public final class PaymillContext {

  private ClientService           clientService;
  private OfferService            offerService;
  private PaymentService          paymentService;
  private PreauthorizationService preauthorizationService;
  private RefundService           refundService;
  private SubscriptionService     subscriptionService;
  private TransactionService      transactionService;
  private WebhookService          webhookService;

  private Client                  httpClient;

  private static ObjectMapper     jacksonParser = new ObjectMapper();

  /**
   * Creates a PAYMILL context with the given apiKey. Connection timeout to PAYMILL by default is set to infinity.
   * @param apiKey
   *          Private key from PAYMILL merchant center.
   */
  public PaymillContext( final String apiKey ) {
    this( apiKey, null );
  }

  /**
   * Creates a PAYMILL context with the given apiKey and timeout.
   * @param apiKey
   *          Private key from PAYMILL merchant center.
   * @param timeout
   *          Timeout in milliseconds for the HTTP connection to PAYMILL. If <code>null</code> or <code>0</code> then an interval
   *          of infinity is declared.
   */
  public PaymillContext( final String apiKey, Integer timeout ) {
    ConvertUtils.register( new DateConverter( null ), Date.class );
    try {
      this.httpClient = new Client();
      this.httpClient.setReadTimeout( timeout );
      this.httpClient.setConnectTimeout( timeout );
      this.httpClient.addFilter( new HTTPBasicAuthFilter( apiKey, StringUtils.EMPTY ) );

      this.clientService = this.getPrivateConstructor( ClientService.class ).newInstance( this.httpClient );
      this.offerService = this.getPrivateConstructor( OfferService.class ).newInstance( this.httpClient );
      this.paymentService = this.getPrivateConstructor( PaymentService.class ).newInstance( this.httpClient );
      this.preauthorizationService = this.getPrivateConstructor( PreauthorizationService.class ).newInstance( this.httpClient );
      this.refundService = this.getPrivateConstructor( RefundService.class ).newInstance( this.httpClient );
      this.subscriptionService = this.getPrivateConstructor( SubscriptionService.class ).newInstance( this.httpClient );
      this.transactionService = this.getPrivateConstructor( TransactionService.class ).newInstance( this.httpClient );
      this.webhookService = this.getPrivateConstructor( WebhookService.class ).newInstance( this.httpClient );
    } catch( Exception exc ) {
      throw new RuntimeException( exc );
    }
  }

  public static ObjectMapper getJacksonParser() {
    return PaymillContext.jacksonParser;
  }

  public ClientService getClientService() {
    return this.clientService;
  }

  public OfferService getOfferService() {
    return this.offerService;
  }

  public PaymentService getPaymentService() {
    return this.paymentService;
  }

  public PreauthorizationService getPreauthorizationService() {
    return this.preauthorizationService;
  }

  public RefundService getRefundService() {
    return this.refundService;
  }

  public SubscriptionService getSubscriptionService() {
    return this.subscriptionService;
  }

  public TransactionService getTransactionService() {
    return this.transactionService;
  }

  public WebhookService getWebhookService() {
    return this.webhookService;
  }

  private <T> Constructor<T> getPrivateConstructor( final Class<T> clazz ) throws Exception {
    Constructor<T> declaredConstructor = clazz.getDeclaredConstructor( Client.class );
    declaredConstructor.setAccessible( true );
    return declaredConstructor;
  }

}
