package org.lastfm.action;

import static org.lastfm.action.ActionType.cm;

import org.lastfm.metadata.Metadata;
import org.lastfm.model.Credentials;

public interface Actions {
	String LOGIN_ID = "login";
	ActionType<ValueAction<Credentials>> LOGIN = cm(LOGIN_ID);
	
	String GET_METADATA = "getMetadata";
	ActionType<EmptyAction> METADATA = cm(GET_METADATA);
	
	String SEND_METADATA = "sendMetadata";
	ActionType<RequestAction<Metadata, Integer>> SEND = cm(SEND_METADATA);
	
	String COMPLETE_METADATA = "completeMetadata";
	ActionType<RequestAction<Metadata, Integer>> COMPLETE = cm(COMPLETE_METADATA);
	
}
