package org.lastfm;

import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import static org.mockito.Mockito.*;


public class TestLoginListener {
	private HelperScrobbler helperScrobbler;
	private MainWindow mainWindow;
	private LoginWindow loginWindow;
	private JLabel label;
	private ScrobblerController controller;

	@Before
	public void initialize(){
		helperScrobbler = mock(HelperScrobbler.class);
		mainWindow = mock(MainWindow.class);
		loginWindow = new LoginWindow();
		
		
		label = mock(JLabel.class);
		when(mainWindow.getLoginLabel()).thenReturn(label );
		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
	}
	
	@Test
	public void shouldFailLogin() throws Exception {
		loginWindow.sendButton.doClick();
		
		verify(label).setText(ApplicationState.LOGIN_FAIL);
	}
	
	@Test
	public void shouldLogin() throws Exception {
		LoginController loginController = mock(LoginController.class);
		controller.loginController = loginController;
		
		when(loginController.login(anyString(), anyString())).thenReturn(ApplicationState.OK);
		
		loginWindow.sendButton.doClick();
		
		when(mainWindow.getLoginLabel()).thenReturn(label);
		verify(label).setText(ApplicationState.LOGGED_AS + "");
	}
	
	
}
