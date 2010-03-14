package org.lastfm;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.Before;
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
public class TestLoginListener extends BaseTestCase{
	private JLabel label;
	private ScrobblerController controller;

	@Autowired
	private LoginWindow loginWindow;

	@Mock
	private HelperScrobbler helperScrobbler;
	@Mock
	private MainWindow mainWindow;
	@Mock
	private JFrame mainWindowFrame;
	
	@Before
	public void initialize(){
		when(mainWindow.getFrame()).thenReturn(mainWindowFrame);
		
		JTable table = mock(JTable.class);
		TableModel model = mock(TableModel.class);
		when(mainWindow.getDescriptionTable()).thenReturn(table);
		when(table.getModel()).thenReturn(model);
		
		label = mock(JLabel.class);
		when(mainWindow.getLoginLabel()).thenReturn(label );
		controller = new ScrobblerController(this.helperScrobbler, this.mainWindow, loginWindow);
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
		verify(label).setText(ApplicationState.LOGGED_AS + "josdem");
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
		verify(mainWindow.getLoginLabel(), never()).setText(anyString());
	}
	
	
}
