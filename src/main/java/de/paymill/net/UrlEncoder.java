/**
 * 
 */
package de.paymill.net;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import de.paymill.PaymillException;
import de.paymill.model.IPaymillObject;

/**
 * Encoder implementation which returns url encoded query strings when encoding
 * objects.
 * 
 * @author Johannes Klose <johannes.klose@raketen-projekte.de>
 */
public class UrlEncoder implements IEncoder {
	
	protected String charset;
	private boolean decodeCamelCase;
	
	public UrlEncoder() {
		charset = "UTF-8";
		setDecodeCamelCase(true);
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
		if (o instanceof IPaymillObject) {
			return encodeBean(o);
		} else if (o instanceof Map) {
			return encodeMap((Map<?, ?>)o);
		} else {
			throw new PaymillException(
				"Unknown object type '%s'. Only objects in package " +
				"'com.chipmunk.model' and instances of 'java.util.Map' " +
				"are supported.", o.getClass().getName()
			);
		}
	}
	
	/**
	 * Encode an bean-like object.
	 * 
	 * @param o
	 * @return
	 */
	protected String encodeBean(Object o) {
		try {
			BeanInfo info = Introspector.getBeanInfo(o.getClass());
		
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			StringBuilder builder = new StringBuilder();
			for (PropertyDescriptor prop : props) {
				String key = prop.getName();
				if ("class".equals(key)) {
					continue;
				}
				Method reader = prop.getReadMethod();
				Object value = reader.invoke(o);
				if (value != null) {
					addKeyValuePair(builder, key, value);
				}
			}
			return builder.toString();
		
		} catch (Exception e) {
			throwReflectEx(o, e);
		}
		throw new PaymillException("Ooops!");
	}
	
	private void throwReflectEx(Object o, Exception ex) {
		throw new PaymillException(
			"Error reading bean of type '%s'.", o.getClass().getName()
		);
	}
	
	/**
	 * Encode a map as an url query string.
	 * 
	 * @param map The map to encode.
	 * @return
	 */
	protected String encodeMap(Map<?, ?> map) {
		StringBuilder builder = new StringBuilder();
		for (Entry<?, ?> entry : map.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key != null && value != null) {
				if ( value instanceof Object[]) {
					Object[] array = (Object[]) value;
					for (Object o : array) {
						addKeyValuePair(builder, key.toString()+"[]", o);
					}
				} else {
					addKeyValuePair(builder, key.toString(), value);
				}
			}
		}
		return builder.toString();
	}
	
	/**
	 * Add an encoded key-value-pair to the string builder.
	 * 
	 * @param builder
	 * @param key
	 * @param value
	 */
	protected void addKeyValuePair(StringBuilder builder, String key, Object value) {
		try {
			if (builder.length() > 0) {
				builder.append('&');
			}
			if (decodeCamelCase) {
				key = key.replaceAll("([a-z])([A-Z])", "$1_$2");
				key = key.toLowerCase();
			}
			if (value instanceof Enum) {
				Enum<?> e = (Enum<?>)value;
				value = e.toString().toLowerCase();
				value = ((String)value).replace('_', '.');
			}
			if (value instanceof IPaymillObject) {
				value = ((IPaymillObject) value).getId();
			}
			builder.append(
				String.format("%s=%s",
					URLEncoder.encode(key, charset),
					URLEncoder.encode(value == null ? "" : value.toString(), charset)
				)
			);
		} catch (UnsupportedEncodingException e) {
			throw new PaymillException(
				"Unsupported or invalid character set encoding '%s'.", charset
			);
		}
	}

	public boolean isDecodeCamelCase() {
		return decodeCamelCase;
	}

	public void setDecodeCamelCase(boolean decodeCamelCase) {
		this.decodeCamelCase = decodeCamelCase;
	}
}
