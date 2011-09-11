package org.lastfm.controller;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.event.Events;
import org.lastfm.event.ValueEvent;
import org.lastfm.helper.LastFMAuthenticator;
import org.lastfm.model.Model;
import org.lastfm.model.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.umass.lastfm.Session;



public class TestLoginController {
	@InjectMocks
	private LoginController controller = new LoginController();
	
	@Mock
	private LastFMAuthenticator lastfmAuthenticator;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private Session session;

	private String username = "josdem";
	private String password = "password";
	private User credentials;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getControlEngine()).thenReturn(controlEngine);
		
		credentials = new User(username, password);
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void shouldLogin() throws Exception {
		when(lastfmAuthenticator.login(username, password)).thenReturn(session);
		
		controller.login(credentials);
		
		verify(lastfmAuthenticator).login(username, password);
		verify(controlEngine).set(Model.CURRENT_USER, credentials, null);
		verify(controlEngine).fireEvent(eq(Events.LOGGED), isA(ValueEvent.class));
	}
	
	@Test
	public void shouldfailAtLogin() throws Exception {
		controller.login(credentials);
		
		verify(lastfmAuthenticator).login(username, password);
		verify(controlEngine, never()).set(Model.CURRENT_USER, credentials, null);
		verify(controlEngine).fireEvent(Events.LOGIN_FAILED);
	}
	
}
