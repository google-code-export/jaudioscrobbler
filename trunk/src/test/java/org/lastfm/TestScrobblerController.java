package org.lastfm;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import static org.mockito.Mockito.*;

public class TestScrobblerController {

	@Test
	public void shouldVerifyMainWindowIsDiasableByDefault() throws Exception {
		HelperScrobbler helperScrobbler = mock(HelperScrobbler.class);
		LoginWindow loginWindow = mock(LoginWindow.class);
		MainWindow mainWindow = new MainWindow();
		new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		assertFalse(mainWindow.getFrame().isEnabled());
	}
}
