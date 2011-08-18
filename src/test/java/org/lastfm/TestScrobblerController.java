package org.lastfm;

import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.table.TableModel;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.ScrobblerController.LoginListener;
import org.lastfm.gui.DescriptionTable;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestScrobblerController {
	@InjectMocks
	private ScrobblerController controller = new ScrobblerController();
	
	@Mock
	private HelperScrobbler helperScrobbler;
	@Captor
	private ArgumentCaptor<ActionEvent> event;
	@Mock
	public JTextField userNameTextField;
	@Mock
	private MainWindow mainWindow;
	@Mock
	private LoginWindow loginWindow;
	@Mock
	private DescriptionTable descriptionTable;
	@Mock
	private TableModel tableModel;

	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		when(descriptionTable.getModel()).thenReturn(tableModel);
		when(mainWindow.getDescriptionTable()).thenReturn(descriptionTable);
		
		
		controller.initialize(helperScrobbler, mainWindow, loginWindow);
	}
	
	@Test
	public void shouldLogin() throws Exception {
		LoginListener loginListener = controller.new LoginListener();
//		loginListener.actionPerformed(event.capture());
	}
	
}
