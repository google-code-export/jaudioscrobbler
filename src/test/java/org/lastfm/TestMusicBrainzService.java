package org.lastfm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class TestMusicBrainzService {
	
	@Test
	public void shouldNotGetAlbumIfNoArtistOrTrackName() throws Exception {
		String artistName = "";
		String trackName = "";
		MusicBrainzService service = new MusicBrainzService();
		assertEquals("", service.getArtist(artistName, trackName));
		
		artistName = "Tiesto";
		assertEquals("", service.getArtist(artistName, trackName));
		
		artistName = "";
		trackName = "Here on Earth";
		assertEquals("", service.getArtist(artistName, trackName));
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		String artistName = "Deadmau5";
		String trackName = "Faxing Berlin";
		
		MusicBrainzService service = new MusicBrainzService();

		TrackService trackService = Mockito.mock(TrackService.class);
		Mockito.when(trackService.getAlbum(artistName, trackName)).thenReturn("Some Kind Of Blue");
		
		service.setTrackService(trackService);
		assertEquals("Some Kind Of Blue", service.getArtist(artistName, trackName));
	}
	
	@Test
	public void shouldReturnTrackNumber() throws Exception {
		String artistName = "Above & Beyond";
		String trackName = "Anjunabeach";
		
		MusicBrainzService service = new MusicBrainzService();
		
		String album = service.getArtist(artistName, trackName);
		assertEquals(12, service.getTrackNumber(album));
	}
}
