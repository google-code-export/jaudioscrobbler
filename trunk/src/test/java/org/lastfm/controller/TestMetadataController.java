package org.lastfm.controller;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.controller.service.MetadataExtractor;
import org.lastfm.event.Events;
import org.lastfm.event.ValueEvent;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.Model;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@SuppressWarnings("unchecked")
public class TestMetadataController {
	@InjectMocks
	private MetadataController controller = new MetadataController();
	
	@Mock
	private JFileChooser fileChooser;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private MetadataExtractor metadataExtractor;
	@Mock
	private File root;
	@Mock
	private ControlEngine controlEngine;

	private List<Metadata> metadataList = new ArrayList<Metadata>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getControlEngine()).thenReturn(controlEngine);
	}
	
	@Test
	public void shouldGetMetadata() throws Exception {
		when(fileChooser.showOpenDialog(null)).thenReturn(JFileChooser.APPROVE_OPTION);
		when(fileChooser.getSelectedFile()).thenReturn(root);
		when(metadataExtractor.extractMetadata(root)).thenReturn(metadataList);
		
		controller.getMetadata();
		
		fileChooserSetupExpectations();
		verify(fileChooser).getSelectedFile();
		verify(controlEngine).fireEvent(eq(Events.DIRECTORY_SELECTED), isA(ValueEvent.class));
		verify(controlEngine).remove(Model.METADATA);
		verify(controlEngine).set(Model.METADATA, metadataList, null);
		verify(metadataExtractor).extractMetadata(root);
		verify(controlEngine).fireEvent(Events.LOADED);
	}

	private void fileChooserSetupExpectations() {
		verify(fileChooser).setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		verify(fileChooser).showOpenDialog(null);
	}
	
	@Test
	public void shouldKnowWhenUserCancel() throws Exception {
		when(fileChooser.showOpenDialog(null)).thenReturn(JFileChooser.CANCEL_OPTION);
		
		controller.getMetadata();
		
		fileChooserSetupExpectations();
		verify(fileChooser, never()).getSelectedFile();
		verify(controlEngine, never()).fireEvent(eq(Events.DIRECTORY_SELECTED), isA(ValueEvent.class));
		verify(metadataExtractor, never()).extractMetadata(root);
		verify(controlEngine, never()).fireEvent(Events.LOADED);
	}
}
