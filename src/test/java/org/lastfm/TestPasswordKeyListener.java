package org.lastfm;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mockito;


public class TestPasswordKeyListener {
	
	@Test
	public void shouldLoginIfUserPressEnterKey() throws Exception {
		String userName = "josdem";
		String password = "secret";

		HelperScrobbler helperScrobbler = Mockito.mock(HelperScrobbler.class);
		
		JTable table = Mockito.mock(JTable.class);
		TableModel model = Mockito.mock(TableModel.class);
		
		MainWindow mainWindow = Mockito.mock(MainWindow.class);
		Mockito.when(mainWindow.getDescritionTable()).thenReturn(table);
		Mockito.when(table.getModel()).thenReturn(model);
		
		
		LoginWindow loginWindow = new LoginWindow();
		
		JLabel loginLabel = Mockito.mock(JLabel.class);
		Mockito.when(mainWindow.getLoginLabel()).thenReturn(loginLabel);
		
		LoginController loginController = Mockito.mock(LoginController.class);
		Mockito.when(loginController.login(userName, password)).thenReturn(ApplicationState.OK);
		
		assertEquals(null, ApplicationState.userName);
		assertEquals(null, ApplicationState.password);
		
		
		ScrobblerController controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		controller.loginController = loginController;
		
		FrameFixture window;
		window = new FrameFixture(loginWindow.getFrame());
		window.show();
		window.textBox("userName").enterText(userName);
		window.textBox("password").enterText(password);
		window.textBox("password").releaseKey(KeyEvent.VK_ENTER);
		
		assertEquals(userName, ApplicationState.userName);
		assertEquals(password, ApplicationState.password);
	}
}
