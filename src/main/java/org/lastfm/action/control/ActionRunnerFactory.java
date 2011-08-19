package org.lastfm.action.control;

import org.lastfm.action.Action;
import org.lastfm.action.ActionObject;
import org.lastfm.action.ActionProcessor;

public interface ActionRunnerFactory {
	public <T extends ActionObject> ActionRunnable createRunner(ActionProcessor<T> commandProcessor, Action<T> command);

}
