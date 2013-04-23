/**
 * 
 */
package de.paymill.net;

/**
 * Encoder interface, specifying the required methods for encoding objects
 * into strings.
 * 
 * @author Johannes Klose <johannes.klose@raketen-projekte.de>
 */
public interface IEncoder {

	/**
	 * Encodes the object and returns a string representation suitable for use
	 * with the other net classes.
	 * 
	 * @param o The object to encode.
	 * @return The encoded string.
	 */
	public String encode(Object o);
	
	/**
	 * Set the character set of the encoded string.
	 * 
	 * @param charset
	 */
	public void setCharset(String charset);
	
}
