package org.lastfm.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Track;


public class TestTrackFinder {
	@InjectMocks
	private TrackFinder trackFinder = new TrackFinder();
	
	@Mock
	private TrackHelper trackHelper;
	@Mock
	private Track track;

	private String artistname = "Sander Van Doorn";
	private String trackname = "The Bottle Hymn 2.0";
	private String album = "The Bottle Hymn 2.0 EP";
	private List<Track> trackList= new ArrayList<Track>();


	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		trackList.add(track);
	}
	
	@Test
	public void shouldNotFindAnyAlbum() throws Exception {
		String result = trackFinder.getAlbum(artistname, trackname);
		
		assertTrue(StringUtils.isEmpty(result));
	}
	
	@Test
	public void shouldGetAlbum() throws Exception {
		setTrackHelperExpectations();

		String result = trackFinder.getAlbum(artistname, trackname);
		
		assertEquals(album, result);
	}
	
	@Test
	public void shouldGetTracknumber() throws Exception {
		int expectedTrack = 2;
		setTrackHelperExpectations();

		String albumFromFinder = trackFinder.getAlbum(artistname, trackname);
		int trackNumber = trackFinder.getTrackNumber(albumFromFinder);
		
		assertEquals(album, albumFromFinder);
		assertEquals(expectedTrack, trackNumber);
	}

	private void setTrackHelperExpectations() throws ServerUnavailableException {
		when(trackHelper.findByTitle(trackname)).thenReturn(trackList);
		when(trackHelper.getArtist(track)).thenReturn(artistname);
		when(trackHelper.getTrackNumber(track)).thenReturn("1");
		when(trackHelper.getAlbum(track)).thenReturn(album);
	}
}
