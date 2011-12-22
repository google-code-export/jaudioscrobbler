package org.lastfm.action;

import org.asmatron.messengine.action.ActionId;
import org.asmatron.messengine.action.EmptyAction;
import org.asmatron.messengine.action.RequestAction;
import org.asmatron.messengine.action.ValueAction;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.User;

import static org.asmatron.messengine.action.ActionId.cm;

public interface Actions {
	String LOGIN_ID = "login";
	ActionId<ValueAction<User>> LOGIN = cm(LOGIN_ID);
	
	String GET_METADATA = "getMetadata";
	ActionId<EmptyAction> METADATA = cm(GET_METADATA);
	
	String SEND_METADATA = "sendMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> SEND = cm(SEND_METADATA);
	
	String COMPLETE_ALBUM_METADATA = "completeAlbumMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> COMPLETE_MUSICBRAINZ = cm(COMPLETE_ALBUM_METADATA);
	
	String COMPLETE_LAST_FM_METADATA = "completeLastfmMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> COMPLETE_LAST_FM = cm(COMPLETE_LAST_FM_METADATA);
	
	String WRITE_METADATA = "writeMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> WRITE = cm(WRITE_METADATA);
}
