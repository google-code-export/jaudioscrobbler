package org.lastfm;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.ViewEngine;
import org.lastfm.action.control.ViewEngineConfigurator;
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
	private ViewEngineConfigurator configurator;
	@Mock
	private MainWindow mainWindow;
	@Mock
	private ViewEngine viewEngine;
	
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
		loginWindow.setAddConfigurator(configurator);
		controller = new ScrobblerController();
	}
	
	
	@Test
	public void shouldHandleIOExceptionWhenLogin() throws Exception {
		LoginController loginController = mock(LoginController.class);
		controller.loginController = loginController;
		
		Throwable ioException = new IOException();
		when(loginController.login(anyString(), anyString())).thenThrow(ioException );
		when(configurator.getViewEngine()).thenReturn(viewEngine);
		
		loginWindow.getSendButton().doClick();
		
		when(mainWindow.getLoginLabel()).thenReturn(label);
		verify(mainWindow.getLoginLabel(), never()).setText(anyString());
	}
	
	
}
