package org.lastfm.helper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.ReleaseList;
import com.slychief.javamusicbrainz.entities.Track;
import com.slychief.javamusicbrainz.entities.TrackList;


public class TestTrackHelper {
	@InjectMocks
	private TrackHelper trackHelper = new TrackHelper();
	
	@Mock
	private Track track;
	@Mock
	private ReleaseList releaseList;
	@Mock
	private Release release;
	@Mock
	private TrackList trackList;

	private List<Release> releases;
	private String trackNumber = "11";
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		releases = new ArrayList<Release>();
		releases.add(release);
	}
	@Test
	public void shouldGetTrackNumber() throws Exception {
		when(track.getReleases()).thenReturn(releaseList);
		when(releaseList.getReleases()).thenReturn(releases);
		when(release.getTrackList()).thenReturn(trackList);
		when(trackList.getOffset()).thenReturn(trackNumber);
		
		assertEquals (trackNumber, trackHelper.getTrackNumber(track));
		
	}
}	
