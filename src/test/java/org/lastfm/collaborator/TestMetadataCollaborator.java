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
	private String album = "album";
	private String genre = "genre";
	private String year = "year";
	private String totalTracks = "totalTracks";
	
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
	
	@Test
	public void shouldGetAlbum() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getAlbum()).thenReturn(album);
		when(metadataTwo.getAlbum()).thenReturn(album);
		
		assertEquals(album, metadataCollaborator.getAlbum());
	}
	
	@Test
	public void shouldNotGetAlbum() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getAlbum()).thenReturn(album);
		when(metadataTwo.getAlbum()).thenReturn("otherAlbum");
		
		assertEquals(StringUtils.EMPTY, metadataCollaborator.getAlbum());
	}
	
	@Test
	public void shouldGetGenre() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getGenre()).thenReturn(genre);
		when(metadataTwo.getGenre()).thenReturn(genre);
		
		assertEquals(genre, metadataCollaborator.getGenre());
	}
	
	@Test
	public void shouldNotGetGenre() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getGenre()).thenReturn(genre);
		when(metadataTwo.getGenre()).thenReturn("otherGenre");
		
		assertEquals(StringUtils.EMPTY, metadataCollaborator.getGenre());
	}
	
	@Test
	public void shouldGetYear() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getYear()).thenReturn(year);
		when(metadataTwo.getYear()).thenReturn(year);
		
		assertEquals(year, metadataCollaborator.getYear());
	}
	
	@Test
	public void shouldNotGetYear() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getYear()).thenReturn(year);
		when(metadataTwo.getYear()).thenReturn("otherYear");
		
		assertEquals(StringUtils.EMPTY, metadataCollaborator.getYear());
	}
	
	@Test
	public void shouldGetTotalTracks() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getTotalTracks()).thenReturn(totalTracks);
		when(metadataTwo.getTotalTracks()).thenReturn(totalTracks);
		
		assertEquals(totalTracks, metadataCollaborator.getTotalTracks());
	}
	
	@Test
	public void shouldNotGetTotalTracks() throws Exception {
		setMetadatasExpectations();
		when(metadataOne.getTotalTracks()).thenReturn(totalTracks);
		when(metadataTwo.getTotalTracks()).thenReturn("otherTotalTracks");
		
		assertEquals(StringUtils.EMPTY, metadataCollaborator.getTotalTracks());
	}

}
