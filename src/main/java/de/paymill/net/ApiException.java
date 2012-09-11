package de.paymill.net;

import de.paymill.PaymillException;

public class ApiException extends PaymillException {
	private static final long serialVersionUID = 2044759710291154337L;
	
	private String code;
	private String field;
	
	public ApiException(String message) {
		super(message);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
}
