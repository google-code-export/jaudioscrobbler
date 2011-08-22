package org.lastfm.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.lastfm.controller.LoginController;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.ApplicationState;
import org.lastfm.LastFMAuthenticator;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.event.Events;
import org.lastfm.model.Credentials;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class TestLoginController {
	@InjectMocks
	private LoginController controller = new LoginController();
	
	@Mock
	private LastFMAuthenticator lastfmAuthenticator;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private ControlEngine controlEngine;

	private String username = "josdem";
	private String password = "password";
	private Credentials credentials;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getControlEngine()).thenReturn(controlEngine);
		
		credentials = new Credentials(username, password);
	}
	
	
	@Test
	public void shouldLogin() throws Exception {
		when(lastfmAuthenticator.login(username, password)).thenReturn(ApplicationState.OK);
		
		controller.login(credentials);
		
		verify(lastfmAuthenticator).login(username, password);
		assertEquals(ApplicationState.username, username);
		assertEquals(ApplicationState.password, password);
		verify(controlEngine).fireEvent(Events.LOGGED);
	}
	
	@Test
	public void shouldfailAtLogin() throws Exception {
		when(lastfmAuthenticator.login(username, password)).thenReturn(ApplicationState.ERROR);
		
		controller.login(credentials);
		
		verify(lastfmAuthenticator).login(username, password);
		verify(controlEngine).fireEvent(Events.LOGIN_FAILED);
	}
	
}
