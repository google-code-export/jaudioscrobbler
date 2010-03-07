package org.lastfm;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mockito;

public class TestScrobblerController {
	
	@Test
	public void shouldVerifyMainWindowDisable() throws Exception {
		HelperScrobbler helperScrobbler = Mockito.mock(HelperScrobbler.class);
		LoginWindow loginWindow = Mockito.mock(LoginWindow.class);
		MainWindow mainWindow = new MainWindow();
		new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		
		assertFalse(mainWindow.getFrame().isEnabled());
	}

}
