package org.lastfm.event;

import java.util.List;

import org.asmatron.messengine.event.EmptyEvent;
import org.asmatron.messengine.event.EventId;
import org.asmatron.messengine.event.ValueEvent;
import org.lastfm.model.Metadata;
import org.lastfm.model.MetadataAlbumValues;
import org.lastfm.model.User;

import static org.asmatron.messengine.event.EventId.ev;

public interface Events {

	String USER_LOGGED = "userLogged";
	EventId<ValueEvent<User>> LOGGED = ev(USER_LOGGED);
	
	String USER_LOGIN_FAILED = "userLoginFailed";
	EventId<EmptyEvent> LOGIN_FAILED = ev(USER_LOGIN_FAILED);
	
	String MUSIC_DIRECTORY_SELECTED = "musicDirectorySelected";
	EventId<ValueEvent<String>> DIRECTORY_SELECTED = ev(MUSIC_DIRECTORY_SELECTED);
	
	String MUSIC_DIRECTORY_SELECTED_CANCEL = "musicDirectorySelectedCancel";
	EventId<EmptyEvent> DIRECTORY_SELECTED_CANCEL = ev(MUSIC_DIRECTORY_SELECTED_CANCEL);
	
	String TRACKS_LOADED = "tracksLoaded";
	EventId<EmptyEvent> LOADED = ev(TRACKS_LOADED);
	
	String DIRECTORY_EMPTY_EVENT = "directoryEmptyEvent";
	EventId<EmptyEvent> DIRECTORY_EMPTY = ev(DIRECTORY_EMPTY_EVENT);
	
	String LOAD_METADATA = "loadMetadata";
	EventId<ValueEvent<List<Metadata>>> LOAD = ev(LOAD_METADATA);
	
	String OPEN_ERROR = "openError";
	EventId<EmptyEvent> OPEN = ev(OPEN_ERROR);
	
	String APPLY_METADATA = "readyToApplyMetadata";
	EventId<ValueEvent<MetadataAlbumValues>> READY_TO_APPLY = ev(APPLY_METADATA);
	
	String COVER_ART_FAILED = "coverArtFailed";
	EventId<ValueEvent<String>> LOAD_COVER_ART = ev(COVER_ART_FAILED);
	
	
}
