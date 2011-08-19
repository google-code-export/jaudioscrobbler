package org.lastfm.action.control;

import org.lastfm.action.ActionObject;
import org.lastfm.action.ActionType;
import org.lastfm.event.EmptyEvent;
import org.lastfm.event.EventObject;
import org.lastfm.event.EventType;
import org.lastfm.event.ValueEvent;
import org.lastfm.model.ModelType;

public interface ControlEngine {
	void fireEvent(EventType<EmptyEvent> type);

	<T extends EventObject> void fireEvent(EventType<T> type, T argument);

	<T> void fireValueEvent(EventType<ValueEvent<T>> type, T argument);

	void start();

	void stop();

	<T> T get(ModelType<T> type);

	<T> void set(ModelType<T> type, T value, EventType<ValueEvent<T>> event);

	<T extends ActionObject> void addActionHandler(ActionType<T> actionType, ActionHandler<T> actionHandler);

	<T extends ActionObject> void removeActionHandler(ActionType<T> action);

}
