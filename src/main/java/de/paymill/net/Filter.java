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

	/**
	 * Adds a new filter criteria.
	 * 
	 * @param key
	 * @param filter
	 */
	public void add(String key, Object filter) {
		data.put(key, filter);
	}

	public Map<String, Object> toMap() {
		return new HashMap<String, Object>(data);
	}
}
