package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
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
public class TestPasswordKeyListener extends BaseTestCase{
	
	@Autowired
	private LoginWindow loginWindow;
	private FrameFixture window;
	
	@Mock
	private HelperScrobbler helperScrobbler;
	
	@After
	public void finalize(){
		loginWindow.getFrame().dispose();
		window.cleanUp();
	}

	@Test
	public void shouldLoginIfUserPressEnterKey() throws Exception {
		String userName = "josdem";
		String password = "secret";

		JTable table = mock(JTable.class);
		TableModel model = mock(TableModel.class);
		
		MainWindow mainWindow = mock(MainWindow.class);
		when(mainWindow.getDescriptionTable()).thenReturn(table);
		when(table.getModel()).thenReturn(model);
		
		JFrame frame = mock(JFrame.class);
		when(mainWindow.getFrame()).thenReturn(frame);
		
		JLabel loginLabel = mock(JLabel.class);
		when(mainWindow.getLoginLabel()).thenReturn(loginLabel);
		
		LoginController loginController = mock(LoginController.class);
		when(loginController.login(userName, password)).thenReturn(ApplicationState.OK);
		
		assertEquals(null, ApplicationState.userName);
		assertEquals(null, ApplicationState.password);
		
		
		ScrobblerController controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		controller.loginController = loginController;
		
		window = new FrameFixture(loginWindow.getFrame());
		window.show();
		window.textBox("userName").enterText(userName);
		window.textBox("password").enterText(password);
		window.textBox("password").releaseKey(KeyEvent.VK_ENTER);
		loginWindow.getFrame().dispose();
		
		assertEquals(userName, ApplicationState.userName);
		assertEquals(password, ApplicationState.password);
	}
}