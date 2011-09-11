package org.lastfm.model;

import de.umass.lastfm.Session;

public class User {
	private final String username;
	private final String password;
	private Session session;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return session;
	}
}
