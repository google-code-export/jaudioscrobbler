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
	
	String COMPLETE_COVER_ART_METADATA = "completeCoverArtMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> COMPLETE_COVER_ART = cm(COMPLETE_COVER_ART_METADATA);
	
	String COMPLETE_YEAR_LAST_FM_METADATA = "completeYearLastfmMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> COMPLETE_YEAR_LAST_FM = cm(COMPLETE_YEAR_LAST_FM_METADATA);
	
	String COMPLETE_GENRE_LAST_FM_METADATA = "completeGenreLastfmMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> COMPLETE_GENRE_LAST_FM = cm(COMPLETE_GENRE_LAST_FM_METADATA);
	
	String WRITE_METADATA = "writeMetadata";
	ActionType<RequestAction<Metadata, ActionResult>> WRITE = cm(WRITE_METADATA);
}
