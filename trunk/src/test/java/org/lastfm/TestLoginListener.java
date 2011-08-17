package org.lastfm;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com) 
 *
 */

public class TestLoginListener {
	private JLabel label;
	private ScrobblerController controller;

	private LoginWindow loginWindow = new LoginWindow();

	@Mock
	private HelperScrobbler helperScrobbler;
	@Mock
	private MainWindow mainWindow;
	
	@Before
	public void initialize(){
		MockitoAnnotations.initMocks(this);
		JTable table = mock(JTable.class);
		TableModel model = mock(TableModel.class);
		when(mainWindow.getDescriptionTable()).thenReturn(table);
		when(table.getModel()).thenReturn(model);
		
		label = mock(JLabel.class);
		when(mainWindow.getLoginLabel()).thenReturn(label);
		loginWindow.getFrame().setVisible(true);
		controller = new ScrobblerController(this.helperScrobbler, this.mainWindow, loginWindow);
	}
	
	@Test
	public void shouldFailLogin() throws Exception {
		loginWindow.getSendButton().doClick();
		
		verify(label).setText(ApplicationState.LOGIN_FAIL);
	}
	
	@Test
	public void shouldLogin() throws Exception {
		LoginController loginController = mock(LoginController.class);
		JButton sendButton = mock(JButton.class);
		controller.loginController = loginController;
		
		when(loginController.login(anyString(), anyString())).thenReturn(ApplicationState.OK);
		when(mainWindow.getSendButton()).thenReturn(sendButton);
		
		loginWindow.getSendButton().doClick();

		when(mainWindow.getLoginLabel()).thenReturn(label);
		verify(label).setText(ApplicationState.LOGGED_AS);
		verify(sendButton).setEnabled(true);
	}
	
	@Test
	public void shouldHandleIOExceptionWhenLogin() throws Exception {
		LoginController loginController = mock(LoginController.class);
		controller.loginController = loginController;
		
		Throwable ioException = new IOException();
		when(loginController.login(anyString(), anyString())).thenThrow(ioException );
		
		loginWindow.sendButton.doClick();
		
		when(mainWindow.getLoginLabel()).thenReturn(label);
		verify(mainWindow.getLoginLabel(), never()).setText(anyString());
	}
	
	
}
