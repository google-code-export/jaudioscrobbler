package org.lastfm;

import static org.junit.Assert.*;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.Metadata;
import org.mockito.Mockito;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class TestSendListener {
	private HelperScrobbler helperScrobbler;
	
	private LoginWindow loginWindow;
	private ScrobblerController controller;
	private Metadata metadata;
	private List<Metadata> metadataList;

	@Before
	public void initialize(){
		helperScrobbler = mock(HelperScrobbler.class);
		loginWindow = mock(LoginWindow.class);
		metadata = mock(Metadata.class);
		
		
	}
	
	@Test
	public void shouldUpdateProgressBar() throws Exception {
		MainWindow mainWindow;
		mainWindow = new MainWindow();
		metadataList = new ArrayList<Metadata>();
		controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		
		metadataList.add(metadata);
		
		controller.metadataList = metadataList;
		
		assertEquals(0, mainWindow.getProgressBar().getValue());
		assertFalse("progressBar should not be visible", mainWindow.getProgressBar().isVisible());

		mainWindow.getSendButton().setEnabled(true);
		mainWindow.getSendButton().doClick();
		
		assertTrue("progressBar should be visible", mainWindow.getProgressBar().isVisible());
		assertFalse("openButton should be enable", mainWindow.getOpenButton().isEnabled());

		Thread.sleep(500);
		
		Mockito.verify(helperScrobbler).send(metadata);
		
		assertEquals(100, mainWindow.getProgressBar().getValue());
		assertEquals(ApplicationState.DONE, mainWindow.getLabel().getText());
		
		assertTrue("completeButton should not be enable", mainWindow.getCompleteButton().isEnabled());
		assertTrue("sendButton should not be enable", mainWindow.getSendButton().isEnabled());
		assertTrue("openButton should not be enable", mainWindow.getOpenButton().isEnabled());
	}
}
