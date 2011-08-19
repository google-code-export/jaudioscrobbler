package org.lastfm.action;

import org.lastfm.event.EventObject;
import org.lastfm.event.EventType;
import org.lastfm.event.Listener;
import org.lastfm.model.ModelType;

public interface ViewEngine {
	<T extends ActionObject> void send(ActionType<T> actionType, T parameter);

	<T> void sendValueAction(ActionType<ValueAction<T>> action, T argument);

	void send(ActionType<EmptyAction> action);

	<V, T> void request(ActionType<RequestAction<V, T>> type, V requestParameter, ResponseCallback<T> callback);

	<T> void request(ActionType<RequestAction<Void, T>> type, ResponseCallback<T> callback);

	<T> T get(ModelType<T> type);

	<T extends EventObject> void removeListener(EventType<T> currentviewchanged, Listener<T> listener);

	<T extends EventObject> void addListener(EventType<T> currentviewchanged, Listener<T> listener);

}