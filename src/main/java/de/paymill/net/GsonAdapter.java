package de.paymill.net;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import de.paymill.PaymillException;
import de.paymill.model.Client;
import de.paymill.model.IPaymillObject;
import de.paymill.model.Interval;
import de.paymill.model.Offer;
import de.paymill.model.Payment;
import de.paymill.model.Refund;
import de.paymill.model.Subscription;
import de.paymill.model.Transaction;
import de.paymill.model.Webhook;

/**
 * Base class for gson encoder/decoder.
 */
public class GsonAdapter {

	/**
	 * Construct a gson instance and add custom serializers/deserializers.
	 * 
	 * @return
	 */
	protected Gson createGson() {
		JsonSerializer<Date> serializer = new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(Date src, Type type,
					JsonSerializationContext context) {
				return src == null ? null : new JsonPrimitive(
						(int) (src.getTime() / 1000));
			}
		};

		JsonDeserializer<Date> deserializer = new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type type,
					JsonDeserializationContext context)
					throws JsonParseException {
				// The API may deliver a date as "false" instead of "null"...
				if ("false".equals(json.getAsString())) {
					return null;
				}
				return json == null ? null : new Date(json.getAsLong() * 1000);
			}
		};

		TypeAdapterFactory enumFactory = new TypeAdapterFactory() {
			public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
				@SuppressWarnings("unchecked")
				final Class<T> rawType = (Class<T>) type.getRawType();
				if (!rawType.isEnum()) {
					return null;
				}

				final Map<String, T> lowercaseToConstant = new HashMap<String, T>();
				for (T constant : rawType.getEnumConstants()) {
					lowercaseToConstant.put(toLowercase(constant), constant);
				}

				return new TypeAdapter<T>() {
					public void write(JsonWriter out, T value)
							throws IOException {
						if (value == null) {
							out.nullValue();
						} else {
							if (rawType.isAssignableFrom(Webhook.EventType.class)) {
								out.value(toLowercase(value).replace('_', '.'));
							} else {
								out.value(toLowercase(value));
							}
						}
					}

					public T read(JsonReader reader) throws IOException {
						if (reader.peek() == JsonToken.NULL) {
							reader.nextNull();
							return null;
						} else {
							String val = reader.nextString();
							if (rawType.isAssignableFrom(Webhook.EventType.class)) {
								val = val.replace('.', '_');
							}
							return lowercaseToConstant.get(val);
						}
					}
				};
			}

			private String toLowercase(Object o) {
				return o.toString().toLowerCase(Locale.US);
			}
		};
		
		JsonSerializer<Interval> intervalSerializer = new JsonSerializer<Interval>() {
			@Override
			public JsonElement serialize(Interval src, Type type,
					JsonSerializationContext context) {
				return src == null ? null : new JsonPrimitive(src.getInterval() + " " + src.getUnit());
			}
		};
		
		JsonDeserializer<Interval> intervalDeserializer = new JsonDeserializer<Interval>() {
			@Override
			public Interval deserialize(JsonElement json, Type type,
					JsonDeserializationContext context)
					throws JsonParseException {
				if (json == null) {
					return null;
				}
				String str = json.getAsString();
				String interval = str.substring(0, str.indexOf(' '));
				String unit = str.substring(str.indexOf(' ') + 1);

				Interval res = new Interval();
				res.setInterval(Integer.parseInt(interval));
				res.setUnit(Interval.Unit.valueOf(unit));
				return res;
			}
		};
		
		return new GsonBuilder()
				.registerTypeAdapter(Date.class, serializer)
				.registerTypeAdapter(Date.class, deserializer)
				.registerTypeAdapter(Interval.class, intervalSerializer)
				.registerTypeAdapter(Interval.class, intervalDeserializer)
				.registerTypeAdapter(Client.class,       new PaymillObjectDeserializer(Client.class,       Client0.class))
				.registerTypeAdapter(Offer.class,        new PaymillObjectDeserializer(Offer.class,        Offer0.class))
				.registerTypeAdapter(Payment.class,      new PaymillObjectDeserializer(Payment.class,      Payment0.class))
				.registerTypeAdapter(Refund.class,       new PaymillObjectDeserializer(Refund.class,       Refund0.class))
				.registerTypeAdapter(Subscription.class, new SubscriptionDeserializer())
				.registerTypeAdapter(Transaction.class,  new PaymillObjectDeserializer(Transaction.class,  Transaction0.class))
				.registerTypeAdapterFactory(enumFactory)
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();
	}
	
	private static class SubscriptionDeserializer implements
			JsonDeserializer<IPaymillObject> {

		@Override
		public IPaymillObject deserialize(JsonElement json, Type type,
				JsonDeserializationContext context) throws JsonParseException {
			if (json == null) {
				return null;
			}

			if (json.isJsonObject()) {
				JsonObject jsonObject = json.getAsJsonObject();
				JsonElement offerElement = jsonObject.get("offer");
				if (offerElement != null && offerElement.isJsonArray()) {
					JsonArray jsonArray = offerElement.getAsJsonArray();
					if (jsonArray.size() == 0) {
						jsonObject.remove("offer");
					} else {
						throw new IllegalArgumentException(
								"Received non empty offer array " + jsonArray);
					}
				}

				return context.deserialize(json, Subscription0.class);
			}
			try {
				String id = json.getAsString();
				IPaymillObject instance = new Subscription();
				instance.setId(id);
				return instance;
			} catch (Exception e) {
				throw new PaymillException("Error instantiating subscription ",
						e);
			}
		}
	}
	
	class PaymillObjectDeserializer implements JsonDeserializer<IPaymillObject> {

		private Class<?> targetClass;
		private Class<?> dummyClass;

		public PaymillObjectDeserializer(Class<?> targetClass,
				Class<?> dummyClass) {
			this.targetClass = targetClass;
			this.dummyClass = dummyClass;
		}

		@Override
		public IPaymillObject deserialize(JsonElement json, Type type,
				JsonDeserializationContext context) throws JsonParseException {
			if (json == null) {
				return null;
			}

			if (json.isJsonObject()) {
				return context.deserialize(json, dummyClass);
			}

			try {
				String id = json.getAsString();
				IPaymillObject instance = (IPaymillObject)targetClass.newInstance();
				instance.setId(id);
				return instance;
			} catch (Exception e) {
				throw new PaymillException("Error instantiating " + targetClass, e);
			}
		}
	};
	
	public static class Client0 extends Client {}
	public static class Offer0 extends Offer {}
	public static class Payment0 extends Payment {}
	public static class Refund0 extends Refund {}
	public static class Subscription0 extends Subscription {}
	public static class Transaction0 extends Transaction {}
}
