package de.paymill.net;

import java.util.HashMap;
import java.util.Map;

/**
 * A thin wrapper around a map object for declaring a list filter when querying
 * the webservice.
 */
public class Filter {

	private Map<String, Object> data;

	public Filter() {
		data = new HashMap<String, Object>();
	}
	
	public Filter(Map<String, Object> data) {
		data = new HashMap<String, Object>(data);
	}

	/**
	 * Adds a new filter criteria.
	 * 
	 * @param key
	 * @param filter
	 * @return this filter. Allows for method chaining.
	 */
	public Filter add(String key, Object filter) {
		data.put(key, filter);
		return this;
	}

	public Map<String, Object> toMap() {
		return new HashMap<String, Object>(data);
	}
}
