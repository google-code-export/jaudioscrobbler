package org.lastfm.util;

public interface JsonReader<T> {
	T read(String json);
}
