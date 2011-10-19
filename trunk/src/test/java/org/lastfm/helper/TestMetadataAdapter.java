package org.lastfm.helper;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.ApplicationState;
import org.lastfm.metadata.Metadata;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestMetadataAdapter {
	private MetadataAdapter adapter = new MetadataAdapter();
	
	@Mock
	private Metadata metadata;
	private String artist = "Daniel Kandi";
	private String title = "Make Me Believe";
	private String album = "Anjunabeats 5";
	private String trackNumber = "5";
	private String totalTracks = "13";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldUpdateArtist() throws Exception {
		String value = artist;
		adapter.update(metadata, ApplicationState.ARTIST_COLUMN, value);
		
		verify(metadata).setArtist(artist);
	}
	
	@Test
	public void shouldUpdateTitle() throws Exception {
		String value = title;
		adapter.update(metadata, ApplicationState.TITLE_COLUMN, value);
		
		verify(metadata).setTitle(title);
	}
	
	@Test
	public void shouldUpdateAlbum() throws Exception {
		String value = album;
		adapter.update(metadata, ApplicationState.ALBUM_COLUMN, value);
		
		verify(metadata).setAlbum(album);
	}
	
	@Test
	public void shouldUpdateTrackNumber() throws Exception {
		String value = trackNumber;
		adapter.update(metadata, ApplicationState.TRACK_NUMBER_COLUMN, value);
		
		verify(metadata).setTrackNumber(trackNumber);
	}
	
	@Test
	public void shouldUpdateTotalTracksNumber() throws Exception {
		String value = totalTracks;
		adapter.update(metadata, ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN, value);
		
		verify(metadata).setTotalTracks(totalTracks);
	}
}
