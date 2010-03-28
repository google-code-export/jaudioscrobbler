package org.lastfm;

import static org.junit.Assert.assertTrue;

import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestScrobblerController extends BaseTestCase{

	@Autowired
	private MainWindow mainWindow;
	@Autowired
	private LoginWindow loginWindow;
	
	@Mock
	private HelperScrobbler helperScrobbler;
	
	@Test
	public void shouldVerifyMainWindowIsEnableByDefault() throws Exception {
		new ScrobblerController(this.helperScrobbler, mainWindow, loginWindow);
		assertTrue(mainWindow.getFrame().isEnabled());
	}
	
	@Test
	public void shouldClickOnLoginLastFM() throws Exception {
		FrameFixture window = new FrameFixture(mainWindow.getFrame());
		window.show();
		window.menuItem("loginMenuItem").click();
		
		assertTrue(this.loginWindow.getFrame().isVisible());
		window.cleanUp();
	}
}
