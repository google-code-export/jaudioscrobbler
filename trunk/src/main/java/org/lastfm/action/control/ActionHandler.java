package org.lastfm.action.control;

import org.lastfm.action.ActionObject;

public interface ActionHandler<T extends ActionObject> {
	void handle(T arg);
}
