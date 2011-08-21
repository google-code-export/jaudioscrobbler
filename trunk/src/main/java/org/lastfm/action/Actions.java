package org.lastfm.action;

import static org.lastfm.action.ActionType.cm;
import org.lastfm.model.Credentials;

public interface Actions {
	String LOGIN_ID = "login";
	ActionType<ValueAction<Credentials>> LOGIN = cm(LOGIN_ID);
	
	String GET_METADATA = "getMetadata";
	ActionType<EmptyAction> METADATA = cm(GET_METADATA);
}
