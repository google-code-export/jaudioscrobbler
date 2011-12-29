package org.lastfm.dnd;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DnDListenerCollection<T extends DragAndDropListener> {
	private final static Log log = LogFactory.getLog(DnDListenerCollection.class);
	private Map<Component, List<T>> listeners = new HashMap<Component, List<T>>();

	public void put(Component component, T listener) {
		List<T> listeners = null;
		synchronized (this.listeners) {
			listeners = this.listeners.get(component);
			if (listeners == null) {
				listeners = new ArrayList<T>();
				this.listeners.put(component, listeners);
			}
		}
		synchronized (listeners) {
			for (T t : listeners) {
				if (t.getClass().equals(listener.getClass()) && t.equals(listener)) {
					log.warn("DUPLICATE HANDLER FOR COMPONENT: " + component + " >> " + listener.getClass().getName());
					return;
				}
			}
			listeners.add(listener);
		}
	}

	public void remove(Component component) {
		listeners.remove(component);
	}

	public void remove(Component component, T listener) {
		List<T> listeners = this.listeners.get(component);
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty()) {
				this.listeners.remove(component);
			}
		}
	}

	public DnDListenerEntries<T> getInmediateEntries(Class<?> clazz, Component component) {
		DnDListenerEntries<T> entries = new DnDListenerEntries<T>();
		if (component == null) {
			return entries;
		}
		List<T> listeners = this.listeners.get(component);
		if (listeners != null) {
			for (T t : listeners) {
				entries.put(clazz, component, t);
			}
		}
		if (entries.isEmpty()) {
			return getInmediateEntries(clazz, component.getParent());
		} else {
			return entries;
		}
	}

	public DnDListenerEntries<T> getUpwardEntries(Class<?> clazz, Component c) {
		DnDListenerEntries<T> dragOverListeners = new DnDListenerEntries<T>();
		addListenersToMotionMap(clazz, c, dragOverListeners);
		return dragOverListeners;
	}

	private void addListenersToMotionMap(Class<?> clazz, Component c, DnDListenerEntries<T> result) {
		if (c == null) {
			return;
		}
		List<T> list = listeners.get(c);
		if (list != null) {
			for (T t : list) {
				result.put(clazz, c, t);
			}
		}
		addListenersToMotionMap(clazz, c.getParent(), result);
	}
}
