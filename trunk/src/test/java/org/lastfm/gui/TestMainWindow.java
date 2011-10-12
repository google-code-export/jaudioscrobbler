package org.lastfm.gui;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.Actions;
import org.lastfm.action.ViewEngine;
import org.lastfm.action.control.ViewEngineConfigurator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMainWindow {
	@InjectMocks
	private MainWindow mainWindow = new MainWindow();
	private FrameFixture window;
	private static final String OPEN_BUTTON_NAME = "openButton";
	
	@Mock
	private ViewEngineConfigurator configurator;
	@Mock
	private ViewEngine viewEngine;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getViewEngine()).thenReturn(viewEngine);
		window = new FrameFixture(mainWindow.getFrame());
		window.show();
	}
	
	@Test
	public void shouldOpen() throws Exception {
		window.button(OPEN_BUTTON_NAME).click();
		
		verify(viewEngine).send(Actions.METADATA);
		assertFalse(mainWindow.getSendButton().isEnabled());
		assertFalse(mainWindow.getCompleteMetadataButton().isEnabled());
		assertFalse(mainWindow.getApplyButton().isEnabled());
	}
	
	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}

}
