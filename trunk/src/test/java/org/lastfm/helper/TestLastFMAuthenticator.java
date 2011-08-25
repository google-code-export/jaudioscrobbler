package org.lastfm.helper;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.ApplicationState;
import org.lastfm.ScrobblerFactory;
import org.lastfm.helper.LastFMAuthenticator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */

public class TestLastFMAuthenticator {
	@InjectMocks
	LastFMAuthenticator controller = new LastFMAuthenticator();

	@Mock
	private Scrobbler scrobbler;
	@Mock
	private ScrobblerFactory factory;
	@Mock
	private ResponseStatus status;
	
	int result;
	
	@Before
	public void initialize(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldLogin() throws Exception {
		String username = "josdem";
		String password = "validPassword";
		
		when(factory.getScrobbler(ApplicationState.CLIENT_SCROBBLER_ID, ApplicationState.CLIENT_SCROBBLER_VERSION, username)).thenReturn(scrobbler);
		when(scrobbler.handshake(password)).thenReturn(status);
		when(status.getStatus()).thenReturn(ResponseStatus.OK);
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.OK, result);
	}
	
	@Test
	public void shouldFailAtLoginIfNoUsernameAndPassword() throws Exception {
		String username = "";
		String password = "";
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.ERROR, result);
		
		notToCallScrobblerServicesAssertion(username, password);
	}

	private void notToCallScrobblerServicesAssertion(String username, String password) throws IOException {
		verify(factory, never()).getScrobbler(ApplicationState.CLIENT_SCROBBLER_ID, ApplicationState.CLIENT_SCROBBLER_VERSION, username);
		verify(scrobbler, never()).handshake(password);
		verify(status, never()).getStatus();
	}
	
	@Test
	public void shouldFailAtLoginIfNoUsername() throws Exception {
		String username = "";
		String password = "somePassword";
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.ERROR, result);
		notToCallScrobblerServicesAssertion(username, password);
	}
	
	@Test
	public void shouldFailAtLoginIfNoPassword() throws Exception {
		String username = "someUsername";
		String password = "";
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.ERROR, result);
		notToCallScrobblerServicesAssertion(username, password);
	}
	
	@Test
	public void shouldFailAtLogin() throws Exception {
		String username = "josdem";
		String password = "invalidPassword";
		
		when(factory.getScrobbler(anyString(), anyString(), anyString())).thenReturn(scrobbler);
		when(scrobbler.handshake(password)).thenReturn(status);
		when(status.getStatus()).thenReturn(ResponseStatus.BADAUTH);
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.FAILURE, result);
	}
}
