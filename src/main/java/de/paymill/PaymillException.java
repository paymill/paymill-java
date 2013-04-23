package de.paymill;

@SuppressWarnings("serial")
public class PaymillException extends RuntimeException {
	public PaymillException() {
		super();
	}
	
	public PaymillException(String message, Object... args) {
		super(String.format(message, args));
	}
	
	public PaymillException(String message, Throwable cause,  Object... args) {
		super(String.format(message, args), cause);
	}
}
