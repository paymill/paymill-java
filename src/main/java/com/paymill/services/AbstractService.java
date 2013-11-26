package com.paymill.services;

import com.sun.jersey.api.client.Client;

class AbstractService {

  protected Client httpClient;

  protected AbstractService( Client httpClient ) {
    this.httpClient = httpClient;
  }

}
