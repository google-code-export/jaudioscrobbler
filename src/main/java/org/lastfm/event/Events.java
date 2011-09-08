package org.lastfm.event;

import static org.lastfm.event.EventType.ev;

import org.lastfm.metadata.Metadata;
import org.lastfm.model.User;

public interface Events {

	String USER_LOGGED = "userLogged";
	EventType<ValueEvent<User>> LOGGED = ev(USER_LOGGED);
	
	String USER_LOGIN_FAILED = "userLoginFailed";
	EventType<EmptyEvent> LOGIN_FAILED = ev(USER_LOGIN_FAILED);
	
	String MUSIC_DIRECTORY_SELECTED = "musicDirectorySelected";
	EventType<ValueEvent<String>> DIRECTORY_SELECTED = ev(MUSIC_DIRECTORY_SELECTED);
	
	String TRACKS_LOADED = "tracksLoaded";
	EventType<EmptyEvent> LOADED = ev(TRACKS_LOADED);
	
	String LOAD_METADATA = "loadMetadata";
	EventType<ValueEvent<Metadata>> LOAD = ev(LOAD_METADATA);
	
	String OPEN_ERROR = "openError";
	EventType<EmptyEvent> OPEN = ev(OPEN_ERROR);
	
	String READY_TO_COMPLETE_METADATA = "readyToCompleteMetadata";
	EventType<EmptyEvent> READY_TO_COMPLETE = ev(READY_TO_COMPLETE_METADATA);
	
}
