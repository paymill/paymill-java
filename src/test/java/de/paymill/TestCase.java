/**
 * 
 */
package de.paymill;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import de.paymill.net.JsonEncoder;

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
		JsonEncoder encoder = new JsonEncoder();
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

		Map<String, Object> card = new HashMap<String, Object>();
		card.put("number", "4111111111111111");
		card.put("exp_month", expMonthStr);
		card.put("exp_year", date.get(Calendar.YEAR) + 1);
		card.put("cvc", "123");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("requesttype", "create_token");
		data.put("merchantkey", apiKey);
		data.put("card", card);

		try {
			SSLHelper.setTrustAllConnections();
			HttpURLConnection connection = (HttpURLConnection) (new URL(url)
					.openConnection());
			connection.setDoOutput(true);
			OutputStream out = connection.getOutputStream();
			out.write(encoder.encode(data).getBytes());
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
			JsonObject root = parser.parse(body).getAsJsonObject();
			JsonElement token = root.get("token");
			if (token == null) {
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
