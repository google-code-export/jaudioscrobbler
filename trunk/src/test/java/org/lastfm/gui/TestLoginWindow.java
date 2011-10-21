package org.lastfm.gui;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.KeyEvent;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.Actions;
import org.lastfm.action.ViewEngine;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.lastfm.model.User;
import org.lastfm.util.Environment;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestLoginWindow {
	@InjectMocks
	private LoginWindow loginWindow = new LoginWindow();

	private static final String USERNAME_TEXTFIELD_NAME = "usernameTextfield";
	private static final String PASSWORD_TEXTFIELD_NAME = "passwordTextfield";
	private static final String SEND_BUTTON_NAME = "sendButton";
	private FrameFixture window;
	private String user = "josdem";
	private String password = "password";

	@Mock
	private ViewEngineConfigurator configurator;
	@Mock
	private ViewEngine viewEngine;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getViewEngine()).thenReturn(viewEngine);
		window = new FrameFixture(loginWindow.getFrame());
		window.show();
	}

	@Test
	public void shouldLoginByActionListener() throws Exception {
		setUsernameAndPassword();
		window.button(SEND_BUTTON_NAME).click();

		verify(viewEngine).sendValueAction(eq(Actions.LOGIN), isA(User.class));
	}

	private void setUsernameAndPassword() {
		window.textBox(USERNAME_TEXTFIELD_NAME).enterText(user);
		window.textBox(PASSWORD_TEXTFIELD_NAME).enterText(password);
	}

	@Test
	public void shouldLoginByKeyListenerInSendButton() throws Exception {
		//Bug FEST in Linux at KeyEvent.VK_ENTER is not working property
		if (!Environment.isLinux()) {

			setUsernameAndPassword();
			window.button(SEND_BUTTON_NAME).pressKey(KeyEvent.VK_ENTER);

			verify(viewEngine).sendValueAction(eq(Actions.LOGIN), isA(User.class));
		}
	}

	@Test
	public void shouldLoginByKeyListenerInPasswordField() throws Exception {
		//Bug FEST in Linux at KeyEvent.VK_ENTER is not working property
		if (!Environment.isLinux()) {
			setUsernameAndPassword();
			window.textBox(PASSWORD_TEXTFIELD_NAME).pressKey(KeyEvent.VK_ENTER);

			verify(viewEngine).sendValueAction(eq(Actions.LOGIN), isA(User.class));
		}
	}

	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}
}
