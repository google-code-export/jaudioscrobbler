package org.lastfm.action;

import static org.lastfm.action.ActionType.cm;

public interface Actions {
	String LOGIN_ID = "login";
	ActionType<ValueAction<String>> LOGIN = cm(LOGIN_ID);
}
