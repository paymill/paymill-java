package com.paymill;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymill.services.ClientService;
import com.paymill.services.OfferService;
import com.paymill.services.PaymentService;
import com.paymill.services.PaymillService;
import com.paymill.services.PreauthorizationService;
import com.paymill.services.RefundService;
import com.paymill.services.SubscriptionService;
import com.paymill.services.TransactionService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public final class Paymill {

  public final static String                                          ENDPOINT = "https://api.paymill.com/v2";

  private static Map<Class<? extends PaymillService>, PaymillService> services;

  @Getter
  private static Client                                               httpClient;

  @Getter
  private static ObjectMapper                                         jacksonParser;

  static {
    Paymill.httpClient = new Client();
    Paymill.jacksonParser = new ObjectMapper();

    Paymill.services = new HashMap<Class<? extends PaymillService>, PaymillService>();

    try {
      Constructor<ClientService> declaredConstructor = ClientService.class.getDeclaredConstructor( null );
      declaredConstructor.setAccessible( true );
      Paymill.services.put( ClientService.class, declaredConstructor.newInstance( null ) );
      Paymill.services.put( OfferService.class, new OfferService() );
      Paymill.services.put( PaymentService.class, new PaymentService() );
      Paymill.services.put( PreauthorizationService.class, new PreauthorizationService() );
      Paymill.services.put( RefundService.class, new RefundService() );
      Paymill.services.put( SubscriptionService.class, new SubscriptionService() );
      Paymill.services.put( TransactionService.class, new TransactionService() );
    } catch( Exception exc ) {
      exc.printStackTrace();
    }
  }

  public static void setApiKey( String apiKey ) {
    Paymill.httpClient.addFilter( new HTTPBasicAuthFilter( apiKey, "" ) );
  }

  public static void refreshApiKey( String apiKey ) {
    Paymill.httpClient.addFilter( new HTTPBasicAuthFilter( apiKey, "" ) );
  }

  @SuppressWarnings( "unchecked" )
  public static <T> T getService( Class<? extends PaymillService> clazz ) {
    return (T) Paymill.services.get( clazz );
  }

}
