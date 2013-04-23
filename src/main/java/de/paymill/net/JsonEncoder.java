/**
 * 
 */
package de.paymill.net;

import com.google.gson.Gson;

/**
 * Encoder implementation which returns json strings when encoding objects.
 * 
 * @author Johannes Klose <johannes.klose@raketen-projekte.de>
 */
public class JsonEncoder implements IEncoder {
	
	protected Gson gson;
	protected String charset;
	
	public JsonEncoder() {
		gson = new Gson();
		charset = "UTF-8";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chipmunk.net.IEncoder#setCharset(java.lang.String)
	 */
	@Override
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/* (non-Javadoc)
	 * @see com.chipmunk.net.IEncoder#encode(java.lang.Object)
	 */
	@Override
	public String encode(Object o) {
		return gson.toJson(o);
	}

}
