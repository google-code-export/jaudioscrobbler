package org.lastfm;

import static org.junit.Assert.assertEquals;
import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.ApplicationState;
import org.lastfm.LoginController;
import org.mockito.Mockito;

public class TestLoginController {
	Scrobbler scrobbler = Mockito.mock(Scrobbler.class);
	ScrobblerFactory factory = Mockito.mock(ScrobblerFactory.class);
	ResponseStatus status;
	LoginController controller;
	String username = "josdem";
	int result;
	
	@Before
	public void initialize(){
		controller = new LoginController();
		controller.factory = factory;
	}
	
	@Test
	public void shouldLogin() throws Exception {
		String password = "validPassword";
		
		status = new ResponseStatus(0);
		Mockito.when(factory.getScrobbler(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(scrobbler);
		Mockito.when(scrobbler.handshake(password)).thenReturn(status);
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.OK, result);
	}
	
	@Test
	public void shouldFailAtLogin() throws Exception {
		String password = "invalidPassword";
		
		status = new ResponseStatus(2);
		Mockito.when(factory.getScrobbler(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(scrobbler);
		Mockito.when(scrobbler.handshake(password)).thenReturn(status);
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.FAILURE, result);
	}
}
