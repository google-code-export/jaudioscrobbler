package org.lastfm;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.table.TableModel;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.ScrobblerController.CompleteListener;
import org.lastfm.ScrobblerController.DescriptionTableModelListener;
import org.lastfm.ScrobblerController.LastFMLoginListener;
import org.lastfm.ScrobblerController.LoginListener;
import org.lastfm.ScrobblerController.OpenListener;
import org.lastfm.ScrobblerController.PasswordKeyListener;
import org.lastfm.ScrobblerController.SendListener;
import org.lastfm.gui.DescriptionTable;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class TestLauncher {
	
	private MainWindow mainWindow;
	private LoginWindow loginWindow;
	private TableModel tableModel;

	@Before
	public void setup() throws Exception {
		ConfigurableApplicationContext applicationContext = mock(ConfigurableApplicationContext.class);
		HelperScrobbler helperScrobbler = mock(HelperScrobbler.class);
		DescriptionTable descriptionTable = mock(DescriptionTable.class);

		mainWindow = mock(MainWindow.class);
		loginWindow = mock(LoginWindow.class);
		tableModel = mock(TableModel.class);
		
		when(applicationContext.getBean(HelperScrobbler.class)).thenReturn(helperScrobbler);
		when(applicationContext.getBean(MainWindow.class)).thenReturn(mainWindow);
		when(applicationContext.getBean(LoginWindow.class)).thenReturn(loginWindow);
		when(descriptionTable.getModel()).thenReturn(tableModel);
		when(mainWindow.getDescriptionTable()).thenReturn(descriptionTable);

		new Launcher(applicationContext);
	}
	
	@Test
	public void shouldValidateListeners() throws Exception {
		verify(mainWindow).addOpenListener(isA(OpenListener.class));
		verify(mainWindow).addSendListener(isA(SendListener.class));
		verify(mainWindow).addCompleteListener(isA(CompleteListener.class));
		verify(mainWindow).addLastFMLoginListener(isA(LastFMLoginListener.class));
		verify(loginWindow).addLoginListener(isA(LoginListener.class));
		verify(loginWindow).addKeyListener(isA(PasswordKeyListener.class));
		verify(tableModel).addTableModelListener(isA(DescriptionTableModelListener.class));
	}

}
