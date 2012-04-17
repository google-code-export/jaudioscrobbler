package org.lastfm.helper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.metadata.Metadata;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class TestMetadataHelper {

	@InjectMocks
	private MetadataHelper metadataHelper = new MetadataHelper();
	
	private List<Metadata> metadatas = new ArrayList<Metadata>();

	private String album = "Lemon Flavored Kiss";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldKnowIfSameAlbum() throws Exception {
		Metadata metadata = mock(Metadata.class);
		Metadata anotherMetadata = mock(Metadata.class);
		when(metadata.getAlbum()).thenReturn(album);
		when(anotherMetadata.getAlbum()).thenReturn(album);
		
		assertTrue(metadataHelper.isSameAlbum(metadatas));
	}

}
