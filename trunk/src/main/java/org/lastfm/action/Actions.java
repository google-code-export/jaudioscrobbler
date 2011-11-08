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
	
	String COMPLETE_ALBUM_METADATA = "completeAlbumMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> COMPLETE_ALBUM = cm(COMPLETE_ALBUM_METADATA);
	
	String COMPLETE_LAST_FM_METADATA = "completeLastfmMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> COMPLETE_LAST_FM = cm(COMPLETE_LAST_FM_METADATA);
	
	String WRITE_METADATA = "writeMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> WRITE = cm(WRITE_METADATA);
}
