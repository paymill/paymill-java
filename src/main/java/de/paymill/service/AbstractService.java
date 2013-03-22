package de.paymill.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.paymill.Paymill;
import de.paymill.PaymillException;
import de.paymill.net.Filter;
import de.paymill.net.HttpClient;

/**
 * The abstract service implements the basic CRUD operations for accessing the
 * ReST webservice. It's used as a base class for the more specialized service
 * implementations.
 */
public class AbstractService<T> {

	protected String resource;
	protected Class<T> modelClass;
	protected ListType listType;
	protected HttpClient client;
	protected Set<String> updateableProperties;

	public AbstractService(String resource, Class<T> modelClass) {
		this(resource, modelClass, Paymill.getClient());
	}

	public AbstractService(String resource, Class<T> modelClass,
			HttpClient client) {
		this.modelClass = modelClass;
		this.listType = new ListType(modelClass);
		this.resource = resource;
		this.client = client;
		this.updateableProperties = new HashSet<String>();
	}

	/**
	 * Loads a list of all objects under this service's resource. This may
	 * consume a huge amount of memory, better use the variant
	 * {@link #list(int, int)} for querying a limited amount of objects.
	 * 
	 * @return
	 */
	public List<T> list() {
		return client.getList(resource, null, listType);
	}

	/**
	 * Same as {@link #list()} but queries for a filtered list as specified by
	 * the given filter criteria.
	 * 
	 * @param filter
	 * @return
	 */
	public List<T> list(Filter filter) {
		return client.getList(resource, filter.toMap(), listType);
	}

	/**
	 * Same as {@link #list()} but only querying a limited amount of objects.
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<T> list(int offset, int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("count", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));
		return client.getList(resource, params, listType);
	}

	/**
	 * Same as {@link #list(int, int)} with an order option.
	 * 
	 * @param offset
	 * @param limit
	 * @param order
	 * @return
	 */
	public List<T> list(int offset, int limit, String order) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("count", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));
		params.put("order", order);
		return client.getList(resource, params, listType);
	}

	/**
	 * Same as {@link #list(int, int)} but querying for a filtered list.
	 * 
	 * @param offset
	 * @param limit
	 * @param filter
	 * @return
	 */
	public List<T> list(int offset, int limit, Filter filter) {
		Map<String, Object> params = filter.toMap();
		params.put("count", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));
		return client.getList(resource, params, listType);
	}

	/**
	 * Queries for a single object by the given identifier.
	 * 
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return client.get(resource, id, modelClass);
	}

	/**
	 * Stores the given object and returns the webservice's version of the newly
	 * created object, including fields automatically filled in by the
	 * webservice.
	 * 
	 * @param obj
	 * @return
	 */
	public T create(T obj) {
		return client.post(resource, obj, modelClass);
	}

	/**
	 * Updates the given object. Returns the new version of the object, as
	 * returned by the webservice.
	 * 
	 * @param obj
	 * @return
	 */
	public T update(T obj) {
		return client.put(resource, getModelId(obj), getParamMap(obj), modelClass);
	}

	/**
	 * Deletes the given object.
	 * 
	 * @param obj
	 */
	public void delete(T obj) {
		client.delete(resource, getModelId(obj));
	}

	/**
	 * Deletes the object object by the given id.
	 * 
	 * @param id
	 */
	public void delete(String id) {
		client.delete(resource, id);
	}
	
	protected Map<String, Object> getParamMap(T obj) {
		try {
			BeanInfo info = Introspector.getBeanInfo(obj.getClass());
			Map<String, Object> params = new HashMap<String, Object>();
			for (PropertyDescriptor d : info.getPropertyDescriptors()) {
				String name = d.getName();
				if (updateableProperties.contains(name)) {
					params.put(name, d.getReadMethod().invoke(obj));
				}
			}
			return params;
		} catch (Exception e) {
			throw new PaymillException("Error loading properties", e);
		}
	}

	protected String getModelId(T obj) {
		try {
			Method method = obj.getClass().getMethod("getId");
			Object result = method.invoke(obj);
			if (result != null) {
				return result.toString();
			}
			throw new PaymillException("Id getter returned null at " + obj);
		} catch (Exception ex) {
			throw new PaymillException("Error reading id property from " + obj,
					ex);
		}
	}

	/**
	 * Helper class for deserializing the parameterized list type.
	 */
	private class ListType implements ParameterizedType {

		private Type[] typeArguments;

		public ListType(Class<T> modelClass) {
			typeArguments = new Type[] { modelClass };
		}

		@Override
		public Type[] getActualTypeArguments() {
			return typeArguments;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}

		@Override
		public Type getRawType() {
			return List.class;
		}
	}
}
