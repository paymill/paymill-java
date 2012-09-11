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
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
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

import de.paymill.model.Card;
import de.paymill.model.Card0;

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
				return json == null ? null : new Date(json.getAsInt() * 1000);
			}
		};
		JsonDeserializer<Card> cardDeserializer = new JsonDeserializer<Card>() {
			@Override
			public Card deserialize(JsonElement json, Type type,
					JsonDeserializationContext context)
					throws JsonParseException {
				if (json == null) {
					return null;
				}

				if (json.isJsonObject()) {
					return context.deserialize(json, Card0.class);
				}

				Card card = new Card();
				card.setId(json.getAsString());
				return card;
			}
		};

		TypeAdapterFactory enumFactory = new TypeAdapterFactory() {
			public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
				@SuppressWarnings("unchecked")
				Class<T> rawType = (Class<T>) type.getRawType();
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
							out.value(toLowercase(value));
						}
					}

					public T read(JsonReader reader) throws IOException {
						if (reader.peek() == JsonToken.NULL) {
							reader.nextNull();
							return null;
						} else {
							return lowercaseToConstant.get(reader.nextString());
						}
					}
				};
			}

			private String toLowercase(Object o) {
				return o.toString().toLowerCase(Locale.US);
			}
		};

		return new GsonBuilder()
				.registerTypeAdapter(Date.class, serializer)
				.registerTypeAdapter(Date.class, deserializer)
				.registerTypeAdapter(Card.class, cardDeserializer)
				.registerTypeAdapterFactory(enumFactory)
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	}
}
