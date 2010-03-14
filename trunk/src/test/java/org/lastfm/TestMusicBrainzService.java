package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class TestMusicBrainzService {
	
	@Test
	public void shouldNotGetAlbumIfNoArtistOrTrackName() throws Exception {
		String artistName = "";
		String trackName = "";
		MusicBrainzService service = new MusicBrainzService();
		assertEquals("", service.getAlbum(artistName, trackName));
		
		artistName = "Tiesto";
		assertEquals("", service.getAlbum(artistName, trackName));
		
		artistName = "";
		trackName = "Here on Earth";
		assertEquals("", service.getAlbum(artistName, trackName));
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		String artistName = "Deadmau5";
		String trackName = "Faxing Berlin";
		
		MusicBrainzService service = new MusicBrainzService();

		TrackService trackService = mock(TrackService.class);
		when(trackService.getAlbum(artistName, trackName)).thenReturn("Some Kind Of Blue");
		
		service.setTrackService(trackService);
		assertEquals("Some Kind Of Blue", service.getAlbum(artistName, trackName));
	}
	
	@Test
	public void shouldReturnTrackNumber() throws Exception {
		String artistName = "Above & Beyond";
		String trackName = "Anjunabeach";
		String album = "Anjunabeach";
		
		MusicBrainzService service = new MusicBrainzService();
		
		TrackService trackService = mock(TrackService.class);
		when(trackService.getAlbum(artistName, trackName)).thenReturn(album);
		when(trackService.getTrackNumber(album)).thenReturn(12);
		
		service.setTrackService(trackService);
		
		assertEquals(album, service.getAlbum(artistName, trackName));
		assertEquals(12, service.getTrackNumber(album));
	}
}
