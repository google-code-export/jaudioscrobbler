package org.lastfm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.ObjectMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.model.AlbumResponse;

public class JsonConverter {

	private static final String FIXED_QUOTES = "\\\"";

	private static final String BUGGY_QUOTES = "\\\\\"";

	private static final JsonConfig CONFIG = new JsonConfig();

	private static final Map<Class<?>, JsonReader<?>> JSON_READERS = new HashMap<Class<?>, JsonReader<?>>();

	private static final List<Class<?>> PRIMITIVES = new ArrayList<Class<?>>();

	private static final Log log = LogFactory.getLog(JsonConverter.class);

	private static final String EMPTY_STRING = "";

	static {
		PRIMITIVES.add(Byte.class);
		PRIMITIVES.add(Short.class);
		PRIMITIVES.add(Integer.class);
		PRIMITIVES.add(Long.class);
		PRIMITIVES.add(Float.class);
		PRIMITIVES.add(Double.class);
		PRIMITIVES.add(Boolean.class);
		PRIMITIVES.add(Character.class);
		PRIMITIVES.add(String.class);
	}

	private JsonConverter() {
	}

	static {
		JSON_READERS.put(AlbumResponse.class, AlbumResponseJsonReader.getInstance());
		CONFIG.setJsonPropertyFilter(new NullValueFilter());
	}

	public static String toJson(Object object) {
		if (object == null) {
			return EMPTY_STRING;
		}
		if (PRIMITIVES.contains(object.getClass())) {
			return object.toString();
		}
		if (object instanceof Collection<?>) {
			return JSONArray.fromObject(object, CONFIG).toString();
		}
		return JSONObject.fromObject(object, CONFIG).toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T toBean(String json, Class<T> clazz) {
		if (json.isEmpty()) {
			return null;
		}
		if (json.contains(BUGGY_QUOTES)) {
			log.warn("Json contains buggy quotes, will replace them.");
			json = json.replaceAll(BUGGY_QUOTES, FIXED_QUOTES);
		}
		JsonReader<T> jsonReader = (JsonReader<T>) JSON_READERS.get(clazz);
		if (jsonReader != null) {
			return (T) jsonReader.read(json);
		}
		if (PRIMITIVES.contains(clazz)) {
			try {
				return clazz.getConstructor(String.class).newInstance(json);
			} catch (Exception e) {
				String message = "Could not convert string '" + json + "' to primtive type " + clazz;
				log.warn(message);
				throw new IllegalArgumentException(message, e);
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		try {
			return (T) JSONObject.toBean(jsonObject, clazz);
		} catch (Exception e) {
			log.warn("Could not convert json to bean of class " + clazz.getName() + " directly.");
			return cleanAndConvert(jsonObject, clazz);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T cleanAndConvert(JSONObject jsonObject, Class<T> clazz) {
		try {
			Iterator<String> keys = jsonObject.keys();
			T instance = clazz.newInstance();
			while (keys.hasNext()) {
				String key = keys.next();
				if (!PropertyUtils.isWriteable(instance, key)) {
					keys.remove();
				}
			}
			return (T) JSONObject.toBean(jsonObject, clazz);
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not convert from json to bean of type " + clazz.getName(), e);
		}
	}

	public static <T extends Collection<V>, V> T toTypedCollection(String json, Class<T> collectionType,
			Class<V> contentType) {
		T collection = null;
		JSONArray jsonCollection = JSONArray.fromObject(json);
		try {
			collection = collectionType.newInstance();
			Iterator<?> iterator = jsonCollection.iterator();
			while (iterator.hasNext()) {
				try {
					if (PRIMITIVES.contains(contentType)) {
						collection.add(contentType.getConstructor(String.class).newInstance(iterator.next().toString()));
					} else {
						collection.add(toBean(iterator.next().toString(), contentType));
					}
				} catch (Exception e) {
					log.warn("Could not read a " + contentType.getSimpleName() + " from a json array.", e);
				}
			}
		} catch (Exception e) {
			return toCollection(json, collectionType);
		}
		return collection;
	}

	public static <T extends Collection<V>, V> T toCollection(String json, Class<T> collectionType) {
		T collection = null;
		JSONArray jsonCollection = JSONArray.fromObject(json);
		try {
			collection = collectionType.newInstance();
			@SuppressWarnings("unchecked")
			T tempCollection = (T) JSONArray.toCollection(jsonCollection);
			collection.addAll(tempCollection);
			return collection;
		} catch (Exception ex) {
			log.error("Could not convert json collection.", ex);
		}
		return collection;
	}

	public static <T> void addJsonReader(Class<T> clazz, JsonReader<T> reader) {
		if (!JSON_READERS.containsKey(clazz)) {
			JSON_READERS.put(clazz, reader);
		}
	}

	private static class NullValueFilter implements PropertyFilter {
		@Override
		public boolean apply(Object object, String key, Object property) {
			return property == null;
		}
	}

	public static void addMorpher(ObjectMorpher morpher) {
		JSONUtils.getMorpherRegistry().registerMorpher(morpher);
	}

}
