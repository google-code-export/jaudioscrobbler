package org.lastfm.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.lastfm.helper.MusicBrainzDelegator;
import org.lastfm.helper.TrackFinder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */

public class TestMusicBrainzDelegator {
	@InjectMocks
	MusicBrainzDelegator service = new MusicBrainzDelegator();
	
	@Mock
	private TrackFinder trackService;
	
	String artistName = "";
	String trackName = "";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldNotGetAlbumIfNoArtistOrTrackName() throws Exception {
		assertEquals("", service.getAlbum(artistName, trackName));
		verify(trackService, never()).getAlbum(artistName, trackName);
	}
	
	@Test
	public void shouldNotGetAlbumIfNoArtist() throws Exception {
		artistName = "Tiesto";
		assertEquals("", service.getAlbum(artistName, trackName));
		verify(trackService, never()).getAlbum(artistName, trackName);
	}
	
	@Test
	public void shouldNotGetAlbumIfNoTrackname() throws Exception {
		trackName = "Here on Earth";
		assertEquals("", service.getAlbum(artistName, trackName));
		verify(trackService, never()).getAlbum(artistName, trackName);
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		artistName = "Deadmau5";
		trackName = "Faxing Berlin";
		when(trackService.getAlbum(artistName, trackName)).thenReturn("Some Kind Of Blue");
		
		assertEquals("Some Kind Of Blue", service.getAlbum(artistName, trackName));
	}
	
	@Test
	public void shouldReturnTrackNumber() throws Exception {
		artistName = "Above & Beyond";
		trackName = "Anjunabeach";
		String album = "Anjunabeach";
		
		when(trackService.getAlbum(artistName, trackName)).thenReturn(album);
		when(trackService.getTrackNumber(album)).thenReturn(12);
		
		assertEquals(album, service.getAlbum(artistName, trackName));
		assertEquals(12, service.getTrackNumber(album));
	}
}
