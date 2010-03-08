package org.lastfm;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestScrobblerController extends BaseTestCase{

	@Autowired
	private MainWindow mainWindow;
	
	@Mock
	private HelperScrobbler helperScrobbler;

	@Test
	public void shouldVerifyMainWindowIsDiasableByDefault() throws Exception {
		LoginWindow loginWindow = mock(LoginWindow.class);
		new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		assertFalse(mainWindow.getFrame().isEnabled());
	}
}
