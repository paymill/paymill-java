/**
 * 
 */
package de.paymill;

import java.lang.reflect.Constructor;

import de.paymill.net.HttpClient;
import de.paymill.service.AbstractService;

/**
 * Your first entry point into the Paymill java library. Here you can set your
 * api key and create service objects for accessing the api.
 */
public class Paymill {
	private static String apiKey;

	/**
	 * Sets the api key used by all subsequent api services.
	 * 
	 * @param apiKey
	 */
	public static void setApiKey(String apiKey) {
		Paymill.apiKey = apiKey;
	}

	/**
	 * Returns the current api key.
	 * 
	 * @return
	 */
	public static String getApiKey() {
		if (apiKey == null) {
			apiKey = System.getProperty("apiKey");
		}
		return apiKey;
	}

	/**
	 * Returns the api url. Can be overwritten by specifying the apiUrl property
	 * on the jvm command line level.
	 * 
	 * @return
	 */
	public static String getApiUrl() {
		return System.getProperty("apiUrl", "https://api.paymill.com/v2");
	}

	/**
	 * Creates a new http client for accessing the api.
	 * 
	 * @return
	 */
	public static HttpClient getClient() {
		String apiKey = getApiKey();
		if (apiKey == null) {
			throw new PaymillException(
					"You need to set an api key before instantiating an HttpClient");
		}
		return new HttpClient(getApiUrl(), apiKey);
	}

	/**
	 * Creates a new api service object instance.
	 * 
	 * @param serviceClass
	 * @return
	 */
	public static <T extends AbstractService<?>> T getService(
			Class<T> serviceClass) {
		try {
			Constructor<T> c = serviceClass.getConstructor(HttpClient.class);
			return c.newInstance(getClient());
		} catch (Exception ex) {
			throw new PaymillException(
					"Error creating a new instance of service " + serviceClass,
					ex);
		}
	}
}
