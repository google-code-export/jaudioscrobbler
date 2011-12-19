package org.lastfm.event;

import org.asmatron.messengine.event.EmptyEvent;
import org.asmatron.messengine.event.EventId;
import org.asmatron.messengine.event.ValueEvent;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.User;

import static org.asmatron.messengine.event.EventId.ev;

public interface Events {

	String USER_LOGGED = "userLogged";
	EventId<ValueEvent<User>> LOGGED = ev(USER_LOGGED);
	
	String USER_LOGIN_FAILED = "userLoginFailed";
	EventId<EmptyEvent> LOGIN_FAILED = ev(USER_LOGIN_FAILED);
	
	String MUSIC_DIRECTORY_SELECTED = "musicDirectorySelected";
	EventId<ValueEvent<String>> DIRECTORY_SELECTED = ev(MUSIC_DIRECTORY_SELECTED);
	
	String TRACKS_LOADED = "tracksLoaded";
	EventId<EmptyEvent> LOADED = ev(TRACKS_LOADED);
	
	String LOAD_METADATA = "loadMetadata";
	EventId<ValueEvent<Metadata>> LOAD = ev(LOAD_METADATA);
	
	String OPEN_ERROR = "openError";
	EventId<EmptyEvent> OPEN = ev(OPEN_ERROR);
	
	String READY_TO_COMPLETE_METADATA = "readyToCompleteMetadata";
	EventId<EmptyEvent> READY_TO_COMPLETE = ev(READY_TO_COMPLETE_METADATA);
	
}
