package com.paymill.exception;

public class PaymillException extends RuntimeException {

  private static final long serialVersionUID = 6388394678517581517L;

  public PaymillException( String message ) {
    super( message );
  }

}
