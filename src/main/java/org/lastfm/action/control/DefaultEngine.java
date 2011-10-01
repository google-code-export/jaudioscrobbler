package org.lastfm.action.control;

import org.lastfm.action.ActionObject;
import org.lastfm.action.ActionType;
import org.lastfm.action.EmptyAction;
import org.lastfm.action.RequestAction;
import org.lastfm.action.ResponseCallback;
import org.lastfm.action.ValueAction;
import org.lastfm.action.ViewEngine;
import org.lastfm.event.EmptyEvent;
import org.lastfm.event.EventObject;
import org.lastfm.event.EventType;
import org.lastfm.event.Listener;
import org.lastfm.event.ValueEvent;
import org.lastfm.model.ModelType;


public class DefaultEngine implements ViewEngine, ControlEngine {
	private final ActionDelegate actionDelegate;
	private final ModelDelegate modelDelegate;
	private final EventDelegate eventDelegate;
	private EngineStatus status = EngineStatus.NEW;
	
	public DefaultEngine() {
		this(new DefaultActionDelegate(), new DefaultEventDelegate(), new DefaultModelDelegate());
	}
	
	protected DefaultEngine(ActionDelegate actionDelegate, EventDelegate eventDelegate, ModelDelegate modelDelegate) {
		this.actionDelegate = actionDelegate;
		this.eventDelegate = eventDelegate;
		this.modelDelegate = modelDelegate;
	}

	@Override
	public <T extends ActionObject> void send(ActionType<T> type, T arg) {
		if (status != EngineStatus.STARTED) {
			throw new IllegalStateException();
		}
		actionDelegate.send(type.create(arg));
	}

	@Override
	public <T> void sendValueAction(ActionType<ValueAction<T>> action, T argument) {
		send(action, new ValueAction<T>(argument));
	}

	@Override
	public void send(ActionType<EmptyAction> type) {
		send(type, EmptyAction.INSTANCE);
	}

	@Override
	public <V, T> void request(ActionType<RequestAction<V, T>> type, V requestParameter, ResponseCallback<T> callback) {
		if (status != EngineStatus.STARTED) {
			throw new IllegalStateException();
		}
		actionDelegate.request(type, requestParameter, callback);
	}

	@Override
	public <T> void request(ActionType<RequestAction<Void, T>> type, ResponseCallback<T> callback) {
		if (status != EngineStatus.STARTED) {
			throw new IllegalStateException();
		}
		actionDelegate.request(type, null, callback);
	}

	@Override
	public <T> T get(ModelType<T> type) {
		if (status == EngineStatus.NEW) {
			throw new IllegalStateException();
		}
		return modelDelegate.get(type);
	}

	@Override
	public <T extends EventObject> void removeListener(EventType<T> type, Listener<T> listener) {
		eventDelegate.removeListener(type, listener);
	}

	@Override
	public <T extends EventObject> void addListener(EventType<T> type, Listener<T> listener) {
		if (status == EngineStatus.STOPED) {
			throw new IllegalStateException();
		}
		eventDelegate.addListener(type, listener);
	}

	@Override
	public void fireEvent(EventType<EmptyEvent> type) {
		fireEvent(type, EmptyEvent.INSTANCE);
	}

	@Override
	public <T extends EventObject> void fireEvent(EventType<T> eventType, T argument) {
		if (status != EngineStatus.STARTED) {
			throw new IllegalStateException();
		}
		eventDelegate.fireEvent(eventType, argument);
	}

	@Override
	public <T> void fireValueEvent(EventType<ValueEvent<T>> type, T argument) {
		fireEvent(type, new ValueEvent<T>(argument));
	}

	@Override
	public void start() {
		status = EngineStatus.STARTED;
		actionDelegate.start();
		eventDelegate.start();
		modelDelegate.start();
	}

	@Override
	public void stop() {
		status = EngineStatus.STOPED;
		actionDelegate.stop();
		eventDelegate.stop();
		modelDelegate.stop();
	}

	@Override
	public <T> void set(ModelType<T> type, T value, EventType<ValueEvent<T>> event) {
		if (status == EngineStatus.STOPED) {
			throw new IllegalStateException();
		}
		modelDelegate.set(type, value);
		if (type.isReadOnly() && event != null) {
			throw new IllegalArgumentException(
					"Setting a readonly should not throw events, they are \"session wide\" values.");
		}
		if (event != null) {
			value = modelDelegate.forceGet(type);
			if (status == EngineStatus.STARTED) {
				eventDelegate.fireEvent(event, new ValueEvent<T>(value));
			} else if (status == EngineStatus.NEW) {
				eventDelegate.fireLater(event, new ValueEvent<T>(value));
			}
		}
	}

	@Override
	public <T extends ActionObject> void addActionHandler(ActionType<T> actionType, ActionHandler<T> actionHandler) {
		if (status == EngineStatus.STOPED) {
			throw new IllegalStateException();
		}
		actionDelegate.addActionHandler(actionType, actionHandler);
	}

	@Override
	public <T extends ActionObject> void removeActionHandler(ActionType<T> action) {
		actionDelegate.removeActionHandler(action);
	}

	@Override
	public <T> void remove(ModelType<T> type) {
		modelDelegate.remove(type);
	}

}
