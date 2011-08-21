package org.lastfm.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCredentials {
	private Credentials credentials;
	
	@Test
	public void shouldCreateCredentials() throws Exception {
		String username = "josdem";
		String password = "password";
		credentials = new Credentials(username, password);
		
		assertEquals(username, credentials.getUsername());
		assertEquals(password, credentials.getPassword());
	}
}
