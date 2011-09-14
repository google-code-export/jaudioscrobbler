package org.lastfm.controller.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.event.Events;
import org.lastfm.event.ValueEvent;
import org.lastfm.metadata.Metadata;
import org.lastfm.util.FileUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestMetadataExtractor {
	@InjectMocks
	private MetadataExtractor metadataExtractor = new MetadataExtractor();
	
	@Mock
	private FileUtils fileUtils;
	@Mock
	private File root;
	@Mock
	private ControlEngineConfigurator configurator;
	@Mock
	private ControlEngine controlEngine;
	
	private List<File> fileList;
	private static final int FIRST_ELEMENT = 0;

	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(configurator.getControlEngine()).thenReturn(controlEngine);
		fileList = new ArrayList<File>();
		fileList.add(new File("src/test/resources/audio/Jaytech - Pepe Garden (Original Mix).mp3"));
	}
	
	@Test
	public void shouldExtractMetadata() throws Exception {
		when(fileUtils.getFileList(root)).thenReturn(fileList);
		
		List<Metadata> metadatas = metadataExtractor.extractMetadata(root);
		Metadata metadata = metadatas.get(FIRST_ELEMENT);
		
		verify(fileUtils).getFileList(root);
		assertEquals("Jaytech", metadata.getArtist());
		verify(controlEngine).fireEvent(Events.LOAD, new ValueEvent<Metadata>(metadata));
	}
}
