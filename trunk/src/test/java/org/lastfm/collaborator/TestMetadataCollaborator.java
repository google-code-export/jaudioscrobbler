package org.lastfm.collaborator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.model.Metadata;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMetadataCollaborator {

	@InjectMocks
	private MetadataCollaborator metadataCollaborator = new MetadataCollaborator();
	
	private List<Metadata> metadatas = new ArrayList<Metadata>();

	@Mock
	private Metadata metadataOne;
	@Mock
	private Metadata metadataTwo;

	private String artist = "artist";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetArtist() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getArtist()).thenReturn(artist);
		when(metadataTwo.getArtist()).thenReturn(artist);
		
		assertEquals(artist, metadataCollaborator.getArtist());
	}

	private void setMetadatasExpectations() {
		metadatas.add(metadataOne);
		metadatas.add(metadataTwo);
		metadataCollaborator.setMetadatas(metadatas);
	}
	
	@Test
	public void shouldNotGetArtist() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getArtist()).thenReturn(artist);
		when(metadataTwo.getArtist()).thenReturn("otherArtist");
		
		assertEquals(StringUtils.EMPTY, metadataCollaborator.getArtist());
	}

}
