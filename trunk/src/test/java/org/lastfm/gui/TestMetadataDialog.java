package org.lastfm.gui;

import static org.mockito.Mockito.when;

import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMetadataDialog {
	private static final String ARTIST_INPUT = "artistInput";
	
	@Mock
	private ControlEngineConfigurator controlEngineConfigurator;
	@Mock
	private ControlEngine controlEngine;
	
	private String message = "message";
	private FrameFixture window;
	private MetadataDialog metadataDialog;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(controlEngineConfigurator.getControlEngine()).thenReturn(controlEngine);
	}
	
	@Test
	public void shoulcCreateMetadataDialog() throws Exception {
		MainWindow mainWindow = new MainWindow();
		metadataDialog = new MetadataDialog(mainWindow, controlEngineConfigurator, message);
		window = new FrameFixture(metadataDialog.getFrame());
		window.show();
		window.textBox(ARTIST_INPUT).enterText("Armin Van Buuren");
	}

}
