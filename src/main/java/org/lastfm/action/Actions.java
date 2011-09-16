package org.lastfm.action;

import static org.lastfm.action.ActionType.cm;

import org.lastfm.metadata.Metadata;
import org.lastfm.model.User;

public interface Actions {
	String LOGIN_ID = "login";
	ActionType<ValueAction<User>> LOGIN = cm(LOGIN_ID);
	
	String GET_METADATA = "getMetadata";
	ActionType<EmptyAction> METADATA = cm(GET_METADATA);
	
	String SEND_METADATA = "sendMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> SEND = cm(SEND_METADATA);
	
	String COMPLETE_METADATA = "completeMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> COMPLETE = cm(COMPLETE_METADATA);
	
	String COMPLETE_ALBUM_METADATA = "completeAlbumMetadata";
	ActionType<ValueAction<Metadata>> COMPLETE_ALBUM = cm(COMPLETE_ALBUM_METADATA);
}
