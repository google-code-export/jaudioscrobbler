package org.lastfm.observ;

public interface Observer<T extends ObserveObject> {
	public void observe(T t);

}
