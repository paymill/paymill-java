package com.paymill.services;

import com.paymill.utils.HttpClient;

class AbstractService {

  protected HttpClient httpClient;

  protected AbstractService( HttpClient httpClient ) {
    this.httpClient = httpClient;
  }

}
