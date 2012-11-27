/**
 * 
 */
package de.paymill;

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
}
