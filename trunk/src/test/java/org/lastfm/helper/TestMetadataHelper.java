package org.lastfm.helper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMetadataHelper {

	@InjectMocks
	private MetadataHelper metadataHelper = new MetadataHelper();

	@Mock
	private Metadata metadata;
	@Mock
	private File file;
	
	private List<Metadata> metadatas = new ArrayList<Metadata>();

	private String album = "Lemon Flavored Kiss";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldKnowIfSameAlbum() throws Exception {
		Metadata anotherMetadata = mock(Metadata.class);
		when(metadata.getAlbum()).thenReturn(album);
		when(anotherMetadata.getAlbum()).thenReturn(album);
		
		assertTrue(metadataHelper.isSameAlbum(metadatas));
	}
	
	@Test
	public void shouldExtractMetadataFromFileWhenDash() throws Exception {
		String filename = "jenifer lopez - 9A - 112.mp3";
		when(file.getName()).thenReturn(filename);
		when(metadata.getFile()).thenReturn(file);
		
		metadataHelper.extractFromFileName(metadata);
		
		verify(metadata).setArtist("jenifer lopez ");
		verify(metadata).setTitle(" 9A ");
	}
	
	@Test
	public void shouldExtractMetadataFromFileWhenNoDash() throws Exception {
		String expectedName = "jenifer lopez";
		String filename = "jenifer lopez.mp3";
		when(file.getName()).thenReturn(filename);
		when(metadata.getFile()).thenReturn(file);
		
		metadataHelper.extractFromFileName(metadata);
		
		verify(metadata).setArtist(expectedName);
		verify(metadata).setTitle(expectedName);
	}
}
