package org.lastfm.helper;

import org.lastfm.ApplicationState;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;

public class AuthenticatorHelper {

	public Session getSession(String username, String password) {
		return Authenticator.getMobileSession(username, password, ApplicationState.KEY, ApplicationState.SECRET);
	}
	
}
