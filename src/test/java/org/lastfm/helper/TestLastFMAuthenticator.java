package org.lastfm.helper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Session;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */

public class TestLastFMAuthenticator {
	@InjectMocks
	private LastFMAuthenticator controller = new LastFMAuthenticator();

	@Mock
	private AuthenticatorHelper authenticatorHelper;
	@Mock
	private Session session;
	
	int result;

	
	@Before
	public void initialize(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldLogin() throws Exception {
		String username = "josdem";
		String password = "validPassword";

		when(authenticatorHelper.getSession(username, password)).thenReturn(session);
		
		assertNotNull(controller.login(username, password));
	}
	
	@Test
	public void shouldFailAtLoginIfNoUsernameAndPassword() throws Exception {
		String username = StringUtils.EMPTY;
		String password = StringUtils.EMPTY;
		
		assertNull(controller.login(username, password));
	}

	@Test
	public void shouldFailAtLoginIfNoUsername() throws Exception {
		String username = StringUtils.EMPTY;
		String password = "somePassword";
		
		assertNull(controller.login(username, password));
	}
	
	@Test
	public void shouldFailAtLoginIfNoPassword() throws Exception {
		String username = "someUsername";
		String password = StringUtils.EMPTY;
		
		assertNull(controller.login(username, password));
	}
	
	@Test
	public void shouldFailAtLogin() throws Exception {
		String username = "josdem";
		String password = "invalidPassword";
		
		assertNull(controller.login(username, password));
	}
}
