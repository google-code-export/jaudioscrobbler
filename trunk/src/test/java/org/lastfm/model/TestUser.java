package org.lastfm.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Session;


public class TestUser {
	private User user;
	private String username = "josdem";
	private String password = "password";
	@Mock
	private Session session;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		user = new User(username, password);
	}

	@Test
	public void shouldGetUsernameAndPassword() throws Exception {
		assertEquals(username, user.getUsername());
		assertEquals(password, user.getPassword());
	}

	@Test
	public void shouldGetSession() throws Exception {
		user.setSession(session);
		assertEquals(session, user.getSession());
	}
}
