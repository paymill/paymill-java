/**
 * 
 */
package de.paymill.net;

import de.paymill.PaymillException;


/**
 * @author jk
 *
 */
@SuppressWarnings("serial")
public class WebserviceException extends PaymillException {
	
	public WebserviceException(String message) {
		super(message);
	}

	public WebserviceException(String message, Object[] args) {
		super(message, args);
	}

}
