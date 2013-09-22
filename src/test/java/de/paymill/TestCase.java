/**
 * 
 */
package de.paymill;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.paymill.model.Transaction;
import de.paymill.net.UrlEncoder;
import de.paymill.service.TransactionService;

/**
 * @author jk
 * 
 */
public class TestCase {

	protected String getRandomEmail() {
		return UUID.randomUUID().toString() + "-test@paymill.de";
	}

	protected String getToken() {
		Calendar now=Calendar.getInstance();
		return getToken("4111111111111111", "123", "12",Integer.toString(now.get(Calendar.YEAR)+1));
	}
	
	protected String getToken(String number, String cvc, String expMonth, String expYear) {
		String url = System.getProperty("tokenUrl",
				"https://test-token.paymill.de");
		String apiKey = System.getProperty("publicApiKey");
		if (apiKey == null) {
			throw new PaymillException(
					"You need to provide an public api key via -DpublicApiKey=<your api key> in order to fetch a token.");
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("channel.id", apiKey);
		data.put("account.number", number);
		data.put("account.expiry.month", expMonth);
		data.put("account.expiry.year", expYear);
		data.put("account.verification", cvc);
		data.put("jsonPFunction", "callback");
		UrlEncoder encoder = new UrlEncoder();
		encoder.setDecodeCamelCase(false);
		String query = encoder.encode(data);

		try {
			SSLHelper.setTrustAllConnections();
			HttpsURLConnection connection = (HttpsURLConnection) (new URL(url
					+ "/?" + query).openConnection());
			
			InputStream stream = connection.getInputStream();
			String body = new Scanner(stream, "UTF-8").useDelimiter("\\A")
					.next();
			stream.close();
			int code = connection.getResponseCode();
			if (code < 200 || code >= 400) {
				throw new PaymillException("Caught http response code " + code
						+ " while creating token at " + url);
			}

			JsonParser parser = new JsonParser();
			JsonObject root = parser
					.parse(body.substring(9, body.length() - 1))
					.getAsJsonObject();
			JsonObject curr = root.getAsJsonObject("transaction");
			JsonElement token = null;
			if (curr != null)
				curr = curr.getAsJsonObject("identification");
			if (curr != null)
				token = curr.get("uniqueId");
			if (curr == null || token == null) {
				throw new PaymillException(
						"Token api returned no token, response = " + body);
			}
			return token.getAsString();
		} catch (IOException e) {
			throw new PaymillException("Can't open connection to token api at "
					+ url, e);
		}
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

	protected Transaction createTransaction() {
		TransactionService transactionService = Paymill.getService(TransactionService.class);
		Transaction transactionParams = new Transaction();
		transactionParams.setToken(getToken());
		transactionParams.setAmount(100);
		transactionParams.setCurrency("EUR");
		Transaction transaction = transactionService.create(transactionParams);
		assertNotNull(transaction);
		assertNotNull(transaction.getId());
		assertEquals((int) transaction.getAmount(), 100);
		assertNotNull(transaction.getPayment());
		return transaction;
	}
}
