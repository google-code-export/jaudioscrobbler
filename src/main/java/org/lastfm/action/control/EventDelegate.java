package org.lastfm.action.control;

import org.lastfm.event.EventObject;
import org.lastfm.event.EventType;
import org.lastfm.event.Listener;

public interface EventDelegate {

	<T extends EventObject> void removeListener(EventType<T> type, Listener<T> listener);

	<T extends EventObject> void addListener(EventType<T> type, Listener<T> listener);

	<T extends EventObject> void fireEvent(EventType<T> type, T argument);

	<T extends EventObject> void fireLater(EventType<T> type, T argument);

	void start();

	void stop();

}
