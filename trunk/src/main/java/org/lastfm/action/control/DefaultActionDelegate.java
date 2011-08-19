package org.lastfm.action.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lastfm.action.Action;
import org.lastfm.action.ActionObject;
import org.lastfm.action.ActionProcessor;
import org.lastfm.action.ActionType;
import org.lastfm.action.RequestAction;
import org.lastfm.action.ResponseCallback;

public class DefaultActionDelegate implements ActionDelegate {
	private Map<ActionType<?>, ActionProcessor<?>> actionCollections = new HashMap<ActionType<?>, ActionProcessor<?>>();
	private ExecutorService actionExecutor;
	private ActionRunnerFactory runnerFactory;

	public DefaultActionDelegate() {
		this(Executors.newCachedThreadPool(new ActionThreadFactory()), new DefaultActionRunnerFactory());
	}

	protected DefaultActionDelegate(ExecutorService executorService, ActionRunnerFactory runnerFactory) {
		actionExecutor = executorService;
		this.runnerFactory = runnerFactory;
	}

	public <T extends ActionObject> void addActionHandler(ActionType<T> command, ActionHandler<T> commandHandler) {
		ActionProcessor<T> commandProcessor = get(command, true);
		commandProcessor.handle(commandHandler);
	}

	@SuppressWarnings({ "unchecked" })
	public void send(Action<?> command) {
		ActionProcessor commandProcessor = get(command.getType(), false);
		actionExecutor.submit(runnerFactory.createRunner(commandProcessor, command));
	}

	@SuppressWarnings("unchecked")
	public <T extends ActionObject> ActionProcessor<T> get(ActionType<T> command, boolean create) {
		ActionProcessor<T> handler = (ActionProcessor<T>) actionCollections.get(command);
		if (handler == null && create) {
			handler = new ActionProcessor<T>(command);
			actionCollections.put(command, handler);
		}
		return handler;
	}

	public <T extends ActionObject> void removeActionHandler(ActionType<T> command) {
		actionCollections.remove(command);
	}

	public void start() {
	}

	public void stop() {
		actionExecutor.shutdown();
		synchronized (this) {
			for (Entry<ActionType<?>, ActionProcessor<?>> entry : actionCollections.entrySet()) {
				entry.getValue().clean();
			}
			actionCollections.clear();
		}
	}

	public <V, T> void request(ActionType<RequestAction<V, T>> type, V requestParameter, ResponseCallback<T> callback) {
		RequestAction<V, T> request = new RequestAction<V, T>(requestParameter, callback);
		Action<RequestAction<V, T>> action = type.create(request);
		send(action);
	}

}
