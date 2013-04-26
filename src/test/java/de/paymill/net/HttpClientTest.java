package de.paymill.net;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Client;

public class HttpClientTest extends TestCase {

	@Test
	public void testGetResourceUrl01()
			throws IOException {
		HttpClient client = new HttpClient("http://paymill.de/", null);
		URL url = client.getResourceUrl("res");
		assertEquals("http://paymill.de/res", url.toString());
	}

	@Test
	public void testGetResourceUrl02()
			throws IOException {
		HttpClient client = new HttpClient("http://paymill.de", null);
		URL url = client.getResourceUrl("res");
		assertEquals("http://paymill.de/res", url.toString());
	}

	@Test
	public void testGetResourceUrl03() throws IOException {
		HttpClient client = new HttpClient("http://paymill.de/", null);
		URL url = client.getResourceUrl("/res");
		assertEquals("http://paymill.de/res", url.toString());
	}
	
	@Test
	public void testGetResourceUrl04() throws IOException {
		HttpClient client = new HttpClient("http://paymill.de/", null);
		URL url = client.getResourceUrl("foo/bar");
		assertEquals("http://paymill.de/foo/bar", url.toString());
	}
	
	@Test
	public void testAppendId() {
		HttpClient client = new HttpClient("http://paymill.de/", null);
		assertEquals("foo/bar", client.appendId("foo", "bar"));
	}
	
	@Test
	public void testErrorInvalidParameter() {
		HttpClient client = Paymill.getClient();
		Map<String,String> params = new HashMap<String,String>();
		params.put("invalidkey", "invalidvalue");
		try {
			client.post("clients", params, Client.class);
			fail("No exception caught");
		} catch(ApiException ex) {
			assertEquals("Api_Exception_InvalidParameter", ex.getCode());
			assertEquals("invalidkey (Api_Exception_InvalidParameter)", ex.getMessage());
		}
	}
	
	@Test
	public void testGetThrowsErrorOnNullId()
	{
		HttpClient client = Paymill.getClient();
		try {
			client.get("foo", null, Client.class);
			fail("No exception caught");
		} catch(IllegalArgumentException ex) {
			assertEquals("Id parameter must not be null.", ex.getMessage());
		}
	}
}
