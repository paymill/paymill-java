package com.paymill.samples.jerseyOneHttp;

import com.paymill.context.PaymillContext;
import com.paymill.models.Client;

public class JerseyClientSample {

  public static void main( String[] args ) {
    JerseyOneClient httpClient = new JerseyOneClient( "<YOUR API KEY>" );
    PaymillContext context = new PaymillContext( httpClient );
    Client client = context.getClientService().createWithDescription( "Sample Client" );
    System.out.println( "Client '" + client.getId() + "' created with description:" + client.getDescription() );
  }
}
