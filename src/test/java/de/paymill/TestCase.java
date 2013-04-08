/**
 * 
 */
package de.paymill;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * @author jk
 * 
 */
public class TestCase {

	protected String getRandomEmail() {
		return UUID.randomUUID().toString() + "-test@paymill.de";
	}

	protected String getToken() {
		return "be269469d9a933174bc40a7842bd1cbc";
	}

	protected URL getWebhookUrl() {
		try {
			return new URL("http://floreysoftsandbox.appspot.com/dummyCallback");
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
