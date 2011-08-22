package org.lastfm.event;

import static org.lastfm.event.EventType.ev;

public interface Events {

	String USER_LOGGED = "userLogged";
	EventType<EmptyEvent> LOGGED = ev(USER_LOGGED);
	
	String USER_LOGIN_FAILED = "userLoginFailed";
	EventType<EmptyEvent> LOGIN_FAILED = ev(USER_LOGIN_FAILED);
	
}
