package org.lastfm.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCredentials {
	private User credentials;
	
	@Test
	public void shouldCreateCredentials() throws Exception {
		String username = "josdem";
		String password = "password";
		credentials = new User(username, password);
		
		assertEquals(username, credentials.getUsername());
		assertEquals(password, credentials.getPassword());
	}
}
