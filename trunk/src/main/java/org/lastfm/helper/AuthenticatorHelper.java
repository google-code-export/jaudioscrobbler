package org.lastfm.helper;

import org.lastfm.Auth;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;

public class AuthenticatorHelper {

	public Session getSession(String username, String password) {
		return Authenticator.getMobileSession(username, password, Auth.KEY, Auth.SECRET);
	}
	
}
