package org.lastfm.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.helper.MusicBrainzDelegator;
import org.lastfm.helper.TrackFinder;
import org.lastfm.model.MusicBrainzTrack;
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
		MusicBrainzTrack result = service.getAlbum(artistName, trackName);
		
		assertTrue(StringUtils.isEmpty(result.getAlbum()));
		assertEquals(-1, result.getTrackNumber());
		verify(trackService, never()).getAlbum(artistName, trackName);
	}
	
	@Test
	public void shouldNotGetAlbumIfNoTrackname() throws Exception {
		artistName = "Tiesto";
		MusicBrainzTrack result = service.getAlbum(artistName, trackName);
		
		assertTrue(StringUtils.isEmpty(result.getAlbum()));
		assertEquals(-1, result.getTrackNumber());
		verify(trackService, never()).getAlbum(artistName, trackName);
	}
	
	@Test
	public void shouldNotGetAlbumIfNoArtist() throws Exception {
		trackName = "Here on Earth";
		MusicBrainzTrack result = service.getAlbum(artistName, trackName);
		
		assertTrue(StringUtils.isEmpty(result.getAlbum()));
		assertEquals(-1, result.getTrackNumber());
		verify(trackService, never()).getAlbum(artistName, trackName);
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		artistName = "Deadmau5";
		trackName = "Faxing Berlin";
		String album = "Some Kind Of Blue";
		MusicBrainzTrack track = new MusicBrainzTrack();
		track.setAlbum(album);
		when(trackService.getAlbum(artistName, trackName)).thenReturn(track);

		MusicBrainzTrack result = service.getAlbum(artistName, trackName);
		
		assertEquals(album, result.getAlbum());
	}
	
	@Test
	public void shouldReturnTrackNumber() throws Exception {
		artistName = "Above & Beyond";
		trackName = "Anjunabeach";
		String album = "Anjunabeach";
		int trackNumber = 12;
		
		MusicBrainzTrack track = new MusicBrainzTrack();
		track.setAlbum(album);
		track.setTrackNumber(trackNumber);
		
		when(trackService.getAlbum(artistName, trackName)).thenReturn(track);
		MusicBrainzTrack result = service.getAlbum(artistName, trackName);
		
		assertEquals(album, result.getAlbum());
		assertEquals(trackNumber, result.getTrackNumber());
	}
}
