package org.lastfm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * 
 * @author Jose Luis De la Cruz
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

		TrackService trackService = Mockito.mock(TrackService.class);
		Mockito.when(trackService.getAlbum(artistName, trackName)).thenReturn("Some Kind Of Blue");
		
		service.setTrackService(trackService);
		assertEquals("Some Kind Of Blue", service.getAlbum(artistName, trackName));
	}
	
	@Test
	public void shouldReturnTrackNumber() throws Exception {
		String artistName = "Above & Beyond";
		String trackName = "Anjunabeach";
		String album = "Anjunabeach";
		
		MusicBrainzService service = new MusicBrainzService();
		
		TrackService trackService = Mockito.mock(TrackService.class);
		Mockito.when(trackService.getAlbum(artistName, trackName)).thenReturn(album);
		Mockito.when(trackService.getTrackNumber(album)).thenReturn(12);
		
		service.setTrackService(trackService);
		
		assertEquals(album, service.getAlbum(artistName, trackName));
		assertEquals(12, service.getTrackNumber(album));
	}
}
