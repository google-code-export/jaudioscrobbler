package org.lastfm;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestScrobblerController {

	@Autowired
	private MainWindow mainWindow;

	@Test
	public void shouldVerifyMainWindowIsDiasableByDefault() throws Exception {
		HelperScrobbler helperScrobbler = mock(HelperScrobbler.class);
		LoginWindow loginWindow = mock(LoginWindow.class);
		new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		assertFalse(mainWindow.getFrame().isEnabled());
	}
}
