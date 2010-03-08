package org.lastfm;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestLoginListener {
	private HelperScrobbler helperScrobbler;
	private MainWindow mainWindow;
	private LoginWindow loginWindow;
	private JLabel label;
	private ScrobblerController controller;
	private JFrame mainWindowFrame;
	
	@Before
	public void initialize(){
		helperScrobbler = mock(HelperScrobbler.class);
		mainWindow = mock(MainWindow.class);
		loginWindow = new LoginWindow();
		
		mainWindowFrame = mock(JFrame.class);
		when(mainWindow.getFrame()).thenReturn(mainWindowFrame);
		
		JTable table = mock(JTable.class);
		TableModel model = mock(TableModel.class);
		when(mainWindow.getDescriptionTable()).thenReturn(table);
		when(table.getModel()).thenReturn(model);
		
		label = mock(JLabel.class);
		when(mainWindow.getLoginLabel()).thenReturn(label );
		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
	}
	
	@After
	public void finalize(){
		loginWindow.getFrame().dispose();
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
		verify(mainWindowFrame).setEnabled(true);
		verify(mainWindowFrame).setVisible(true);
	}
	
	@Test
	public void shouldHandleIOExceptionWhenLogin() throws Exception {
		LoginController loginController = mock(LoginController.class);
		controller.loginController = loginController;
		
		Throwable ioException = new IOException();
		when(loginController.login(anyString(), anyString())).thenThrow(ioException );
		
		loginWindow.sendButton.doClick();
		
		when(mainWindow.getLoginLabel()).thenReturn(label);
		verify(mainWindow.getLoginLabel(), Mockito.never()).setText(Mockito.anyString());
	}
	
	
}
