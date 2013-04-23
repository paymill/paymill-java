/**
 * 
 */
package de.paymill.net;


/**
 * @author jk
 *
 */
public class StatusResponse {
	
	protected int code;
	protected String body;
	
	public StatusResponse(int code, String body) {
		this.code = code;
		this.body = body;
	}
	
	public String getBody() {
		return body;
	}
}
