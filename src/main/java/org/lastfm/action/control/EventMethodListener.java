package org.lastfm.action.control;

import java.lang.reflect.Method;

import org.lastfm.event.EventExecutionMode;
import org.lastfm.event.EventObject;
import org.lastfm.event.Listener;
import org.lastfm.event.ValueEvent;


public class EventMethodListener extends MethodInvoker implements Listener<EventObject> {
	private final EventExecutionMode eventMode;
	private final boolean eager;

	public EventMethodListener(Object object, Method method, EventExecutionMode eventMode, boolean eager) {
		super(object, method);
		if (method.getParameterTypes().length > 1) {
			throw new IllegalMethodException("Illegal Engine binding on: " + object.getClass().getName() + "." + method.getName());
		}
		this.eventMode = eventMode;
		this.eager = eager;
	}

	@Override
	public void handleEvent(EventObject eventArgs) {
		if (eventArgs instanceof ValueEvent) {
			Object o = ((ValueEvent<?>) eventArgs).getValue();
			if (canInvoke(o)) {
				invoke(o);
				return;
			}
		}
		invoke(eventArgs);
	}

	@Override
	public EventExecutionMode getMode() {
		return eventMode;
	}

	@Override
	public boolean isEager() {
		return eager;
	}
}