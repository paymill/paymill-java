package de.paymill.net;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

import de.paymill.PaymillException;

/**
 * An adapter for accessing the Paymill webservice.
 */
public class HttpClient {

	protected URL apiUrl;
	protected String apiKey;
	protected UrlEncoder urlEncoder;
	protected JsonDecoder jsonDecoder;

	public enum Method {
		GET, POST, PUT, DELETE;
	}

	public HttpClient(String apiUrl, String apiKey) {
		this(urlObject(apiUrl), apiKey);
	}

	public HttpClient(URL apiUrl, String apiKey) {
		this.apiUrl = apiUrl;
		this.apiKey = apiKey;
		this.urlEncoder = new UrlEncoder();
		this.jsonDecoder = new JsonDecoder();
	}

	private static URL urlObject(String url) {
		try {
			return new URL(url);
		} catch (IOException ex) {
			throw new PaymillException("Invalid url provided: %s", url);
		}
	}

	/**
	 * Used for retrieving a list from the webservice. Requires the
	 * <tt>listType</tt> parameter to specify the parameterized {@link List}
	 * type. Otherwise, the contents of the list won't be correctly
	 * deserialized.
	 * 
	 * This method triggers a GET request without specifying a resource id.
	 * 
	 * @param resource
	 * @param params
	 * @param listType
	 * @return
	 * @throws ApiException
	 *             if invalid parameters are specified.
	 */
	public <T> List<T> getList(String resource, Map<String, Object> params,
			Type listType) {
		return request(resource, params, Method.GET, listType);
	}

	/**
	 * Retrieves a single resource from the server. Triggers a GET request using
	 * a single resource id.
	 * 
	 * @param resource
	 * @param id
	 * @param resultClass
	 * @return
	 * @throws ApiException
	 *             if the object doesn't exist.
	 */
	public <T> T get(String resource, String id, Class<T> resultClass) {
		if (id == null) {
			throw new IllegalArgumentException("Id parameter must not be null.");
		}
		return request(appendId(resource, id), null, Method.GET, resultClass);
	}

	/**
	 * Creates a new object through the webservice. Triggers a POST request and
	 * returns the newly created object on success.
	 * 
	 * @param resource
	 * @param model
	 * @param resultClass
	 * @return
	 * @throws ApiException
	 *             if an error occurs while creating the object.
	 */
	public <T> T post(String resource, Object model, Class<T> resultClass) {
		return request(resource, model, Method.POST, resultClass);
	}

	/**
	 * Updates the given object through the webservice. Triggers a PUT request
	 * and returns the updated object on success.
	 * 
	 * @param resource
	 * @param id
	 * @param model
	 * @param resultClass
	 * @return
	 * @throws ApiException
	 *             if an error occurs while updating the object.
	 */
	public <T> T put(String resource, String id, Object model,
			Class<T> resultClass) {
		return request(appendId(resource, id), model, Method.PUT, resultClass);
	}

	/**
	 * Triggers a DELETE request using the given resource and object id-
	 * 
	 * @param resource
	 * @param id
	 */
	public void delete(String resource, String id) {
		request(appendId(resource, id), null, Method.DELETE, null);
	}

	/**
	 * Executes a http request and returns the deserialized response object as
	 * specified with the {@link Type} parameter.
	 * 
	 * @param path
	 * @param params
	 * @param method
	 * @param resultType
	 * @return
	 */
	public <T> T request(String path, Object params, Method method,
			Type resultType) {
		URL url = getResourceUrl(path);
		HttpURLConnection connection = createConnection(url, params, method);

		try {
			int code = connection.getResponseCode();
			if (code >= 200 && code < 300) {
				return decode(connection.getInputStream(), resultType);
			} else {
				return decode(connection.getErrorStream(), resultType);
			}
		} catch (IOException ex) {
			throw new PaymillException("Error connecting to the api", ex);
		}
	}

	public <T> T decode(InputStream inputStream, Type resultType) throws IOException {
		String body = readResponseBody(inputStream);
		if (resultType == null) {
			return null;
		}
		return jsonDecoder.decode(body, resultType);
	}
	
	/**
	 * Append the identifier to an resource and only add single slash between
	 * them
	 * 
	 * @param resource
	 * @param identifier
	 * @return
	 */
	protected String appendId(String resource, Object identifier) {
		if (identifier == null) {
			return resource;
		}
		String identifierString = identifier.toString();
		if (resource.charAt(resource.length() - 1) == '/') {
			return resource + identifierString;
		} else {
			return resource + '/' + identifierString;
		}
	}

	/**
	 * Creates an {@link HttpURLConnection} suitable for accessing the ReST
	 * webservice.
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @return
	 */
	protected HttpURLConnection createConnection(URL url, Object params,
			Method method) {

		String query = null;
		if (params != null) {
			query = urlEncoder.encode(params);
		}

		if (query != null && (method == Method.GET || method == Method.DELETE)) {
			url = urlObject(url.toString() + '?' + query);
		}

		try {
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod(method.name());

			if (apiKey != null) {
				setAuthentication(connection);
			}

			if (query != null
					&& !(method == Method.GET || method == Method.DELETE)) {
				connection.setDoOutput(true);
				connection.getOutputStream().write(query.getBytes());
			}

			return connection;
		} catch (IOException ex) {
			throw new PaymillException("Error opening connection", ex);
		}
	}

	protected void setAuthentication(HttpURLConnection connection) {
		String authInfo = apiKey + ":";
		String encodedAuth = Base64.encodeBase64String(authInfo.getBytes());
		connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
	}

	protected URL getResourceUrl(String resource) {
		StringBuilder url = new StringBuilder().append(apiUrl.toString());

		if (url.charAt(url.length() - 1) != '/') {
			url.append('/');
		}

		if (resource.charAt(0) == '/') {
			url.append(resource.substring(1));
		} else {
			url.append(resource);
		}

		try {
			return new URL(url.toString());
		} catch (MalformedURLException e) {
			throw new PaymillException("Malformed url: %s", url.toString());
		}
	}

	protected String readResponseBody(InputStream stream) throws IOException {
		Scanner s = new Scanner(stream, "UTF-8").useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}