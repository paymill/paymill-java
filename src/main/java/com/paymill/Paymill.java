package com.paymill;

import java.lang.reflect.Constructor;

import lombok.Getter;

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

public final class Paymill {

  private ClientService           clientService;
  private OfferService            offerService;
  private PaymentService          paymentService;
  private PreauthorizationService preauthorizationService;
  private RefundService           refundService;
  private SubscriptionService     subscriptionService;
  private TransactionService      transactionService;
  private WebhookService          webhookService;

  @Getter
  private Client                  httpClient;

  @Getter
  private static ObjectMapper     jacksonParser = new ObjectMapper();

  public Paymill( final String apiKey ) {
    try {
      this.httpClient = new Client();
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
      exc.printStackTrace();
      throw new RuntimeException( exc );
    }
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
    return webhookService;
  }

  private <T> Constructor<T> getPrivateConstructor( final Class<T> clazz ) throws Exception {
    Constructor<T> declaredConstructor = clazz.getDeclaredConstructor( Client.class );
    declaredConstructor.setAccessible( true );
    return declaredConstructor;
  }

}
