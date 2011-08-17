package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.Metadata;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class TestSendListener {
	
	private ScrobblerController controller;
	private List<Metadata> metadataList;

	private MainWindow mainWindow;

	@Mock
	private HelperScrobbler helperScrobbler;
	@Mock
	private LoginWindow loginWindow;
	@Mock
	private Metadata metadata;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		mainWindow = new MainWindow();
	}
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
	}
	
	@Test
	public void shouldUpdateProgressBar() throws Exception {
		metadataList = new ArrayList<Metadata>();
		controller = new ScrobblerController(this.helperScrobbler, mainWindow, this.loginWindow);
		
		metadataList.add(metadata);
		
		controller.metadataList = metadataList;
		
		assertEquals(0, mainWindow.getProgressBar().getValue());
		assertFalse("progressBar should not be visible", mainWindow.getProgressBar().isVisible());

		mainWindow.getSendButton().setEnabled(true);
		mainWindow.getSendButton().doClick();
		
		assertTrue("progressBar should be visible", mainWindow.getProgressBar().isVisible());
		assertFalse("openButton should be enable", mainWindow.getOpenButton().isEnabled());

		Thread.sleep(2000);
		
		verify(helperScrobbler).send(metadata);
		
		assertEquals(100, mainWindow.getProgressBar().getValue());
		assertEquals(ApplicationState.DONE, mainWindow.getLabel().getText());
		
		assertTrue("completeButton should not be enable", mainWindow.getCompleteButton().isEnabled());
		assertTrue("sendButton should not be enable", mainWindow.getSendButton().isEnabled());
		assertTrue("openButton should not be enable", mainWindow.getOpenButton().isEnabled());
	}
}
