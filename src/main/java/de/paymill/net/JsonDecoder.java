package de.paymill.net;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import de.paymill.PaymillException;

/**
 */
public class JsonDecoder extends GsonAdapter implements IDecoder {

	protected Gson gson;
	protected JsonParser parser;
	protected String charset;

	public JsonDecoder() {
		gson = createGson();
		parser = new JsonParser();
		charset = "UTF-8";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chipmunk.net.IDecoder#decode(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T decode(String data, Type type) {
		try {
			JsonObject root = parser.parse(data).getAsJsonObject();
			if (root.has("data")) {
				return gson.fromJson(root.get("data"), type);
			} else if (root.has("event")) {
				return gson.fromJson(root.get("event"), type);
			} else if (root.has("error")) {
				throw decodeError(root);
			} else {
				throw new ApiException("Unknown response received: " + data);
			}
		} catch (JsonSyntaxException e) {
			throw new PaymillException("Error decoding string: %s.", e, data);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.paymill.net.IDecoder#decodeError(java.lang.String)
	 */
	protected ApiException decodeError(JsonObject root) {
		String errorMessage = null;
		String errorCode = null;
		String errorField = null;
		try {
			JsonElement errorRoot = root.get("error");

			if (errorRoot.isJsonObject()) {
				JsonObject errorSub = errorRoot.getAsJsonObject();
				JsonObject messages = errorSub.get("messages")
						.getAsJsonObject();
				errorField = errorSub.get("field").getAsString();

				for (Entry<String, JsonElement> entry : messages.entrySet()) {
					errorMessage = entry.getValue().getAsString();
					errorCode = entry.getKey();
					errorMessage += " (" + errorField + ")";
					break;
				}
			} else {
				errorMessage = errorRoot.getAsString();
				errorCode = root.get("exception").getAsString();
				errorMessage += " (" + errorCode + ")";
			}
		} catch ( IllegalStateException e ) {
			errorMessage ="Internal API error, response data=" + root;
		}
		ApiException ex = new ApiException(errorMessage);
		ex.setCode(errorCode);
		ex.setField(errorField);
		return ex;
	}

	@Override
	public void setCharset(String charset) {
		this.charset = charset;
	}

}
