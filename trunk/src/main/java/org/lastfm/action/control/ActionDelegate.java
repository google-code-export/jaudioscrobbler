package org.lastfm.action.control;

import org.lastfm.action.Action;
import org.lastfm.action.ActionObject;
import org.lastfm.action.ActionType;
import org.lastfm.action.RequestAction;
import org.lastfm.action.ResponseCallback;

public interface ActionDelegate {

	void send(Action<?> command);

	<V, T> void request(ActionType<RequestAction<V, T>> type, V requestParameter, ResponseCallback<T> callback);

	<T extends ActionObject> void addActionHandler(ActionType<T> actionType, ActionHandler<T> actionHandler);

	<T extends ActionObject> void removeActionHandler(ActionType<T> action);

	void start();

	void stop();

}
