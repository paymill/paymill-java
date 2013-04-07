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
		return "098f6bcd4621d373cade4e832627b4f6";
	}

	protected URL getWebhookUrl() {
		try {
			return new URL("http://floreysoftsandbox.appspot.com/dummyCallback");
		} catch (MalformedURLException e) {
			return null;
		}
	}

	protected String getWebhookEmail() {
		return "test@test.de";
	}
}
