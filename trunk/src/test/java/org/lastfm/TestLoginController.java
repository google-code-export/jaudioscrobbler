package org.lastfm;

import static org.junit.Assert.assertEquals;
import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class TestLoginController extends BaseTestCase{

	@Mock
	Scrobbler scrobbler;
	@Mock
	ScrobblerFactory factory;
	
	ResponseStatus status;
	LoginController controller;
	
	int result;
	
	@Before
	public void initialize(){
		controller = new LoginController();
		controller.factory = factory;
	}
	
	@Test
	public void shouldLogin() throws Exception {
		String username = "josdem";
		String password = "validPassword";
		
		status = new ResponseStatus(0);
		when(factory.getScrobbler(anyString(), anyString(), anyString())).thenReturn(scrobbler);
		when(scrobbler.handshake(password)).thenReturn(status);
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.OK, result);
	}
	
	@Test
	public void shouldFailAtLoginIfNoUsernameAndPassword() throws Exception {
		String username = "";
		String password = "";
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.ERROR, result);
	}
	
	@Test
	public void shouldFailAtLoginIfNoUsername() throws Exception {
		String username = "";
		String password = "somePassword";
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.ERROR, result);
	}
	
	@Test
	public void shouldFailAtLoginIfNoPassword() throws Exception {
		String username = "someUsername";
		String password = "";
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.ERROR, result);
	}
	
	@Test
	public void shouldFailAtLogin() throws Exception {
		String username = "josdem";
		String password = "invalidPassword";
		
		status = new ResponseStatus(2);
		when(factory.getScrobbler(anyString(), anyString(), anyString())).thenReturn(scrobbler);
		when(scrobbler.handshake(password)).thenReturn(status);
		
		result = controller.login(username, password);
		
		assertEquals(ApplicationState.FAILURE, result);
	}
}
