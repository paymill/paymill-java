/**
 * 
 */
package de.paymill;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.paymill.net.UrlEncoder;

/**
 * @author jk
 * 
 */
public class TestCase {

	protected String getRandomEmail() {
		return UUID.randomUUID().toString() + "-test@paymill.de";
	}

	protected String getToken() {
		Calendar date = Calendar.getInstance();
		String url = System.getProperty("tokenUrl",
				"https://test-token.paymill.de");
		String apiKey = System.getProperty("publicApiKey");
		if (apiKey == null) {
			throw new PaymillException(
					"You need to provide an public api key via -DpublicApiKey=<your api key> in order to fetch a token.");
		}

		int expMonth = date.get(Calendar.MONTH) + 1;
		String expMonthStr = null;
		if (expMonth < 10) {
			expMonthStr = "0" + Integer.toString(expMonth);
		} else {
			expMonthStr = Integer.toString(expMonth);
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("channel.id", apiKey);
		data.put("account.number", "4111111111111111");
		data.put("account.expiry.month", expMonthStr);
		data.put("account.expiry.year", date.get(Calendar.YEAR) + 1);
		data.put("account.verification", "123");
		data.put("jsonPFunction", "callback");
		UrlEncoder encoder = new UrlEncoder();
		encoder.setDecodeCamelCase(false);
		String query = encoder.encode(data);

		try {
			SSLHelper.setTrustAllConnections();
			HttpURLConnection connection = (HttpURLConnection) (new URL(url
					+ '?' + query).openConnection());
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
}
