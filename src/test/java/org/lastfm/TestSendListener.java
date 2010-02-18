package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.mockito.Mockito;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class TestSendListener {
	private HelperScrobbler helperScrobbler;
	private MainWindow mainWindow;
	private LoginWindow loginWindow;
	private ScrobblerController controller;

	@Before
	public void initialize(){
		helperScrobbler = mock(HelperScrobbler.class);
		loginWindow = mock(LoginWindow.class);
		mainWindow = new MainWindow();
		
		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
	}
	
	@Test
	public void shouldUpdateProgressBar() throws Exception {
		List<Metadata> metadataList = new ArrayList<Metadata>();
		
		Metadata metadata = mock(Metadata.class);
		
		metadataList.add(metadata);
		controller.metadataList = metadataList;
		
		assertEquals(0, mainWindow.getProgressBar().getValue());
		mainWindow.sendButton.doClick();
		Thread.sleep(500);
		Mockito.verify(helperScrobbler).send(metadata);
		assertEquals(ApplicationState.DONE, mainWindow.getLabel().getText());
		assertEquals(100, mainWindow.getProgressBar().getValue());
		assertEquals(ApplicationState.DONE, mainWindow.getLabel().getText());
	}
	
}
