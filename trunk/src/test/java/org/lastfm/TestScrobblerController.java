package org.lastfm;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestScrobblerController extends BaseTestCase{

	@Autowired
	private MainWindow mainWindow;
	
	@Mock
	private HelperScrobbler helperScrobbler;
	@Mock
	LoginWindow loginWindow;
	
	@Test
	public void shouldVerifyMainWindowIsDiasableByDefault() throws Exception {
		new ScrobblerController(this.helperScrobbler, mainWindow, this.loginWindow);
		assertFalse(mainWindow.getFrame().isEnabled());
	}
}
