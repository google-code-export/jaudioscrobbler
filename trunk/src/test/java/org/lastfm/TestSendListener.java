package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.Metadata;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestSendListener extends BaseTestCase{
	
	private ScrobblerController controller;
	private List<Metadata> metadataList;

	@Autowired
	private MainWindow mainWindow;

	@Mock
	private HelperScrobbler helperScrobbler;
	@Mock
	private LoginWindow loginWindow;
	@Mock
	private Metadata metadata;
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
	}
	
	@Test
	public void shouldUpdateProgressBar() throws Exception {
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

		Thread.sleep(2500);
		
		verify(helperScrobbler).send(metadata);
		
		assertEquals(100, mainWindow.getProgressBar().getValue());
		assertEquals(ApplicationState.DONE, mainWindow.getLabel().getText());
		
		assertTrue("completeButton should not be enable", mainWindow.getCompleteButton().isEnabled());
		assertTrue("sendButton should not be enable", mainWindow.getSendButton().isEnabled());
		assertTrue("openButton should not be enable", mainWindow.getOpenButton().isEnabled());
	}
}
