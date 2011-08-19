package org.lastfm.action;

public interface ResponseCallback<T> {
	void onResponse(T t);
}