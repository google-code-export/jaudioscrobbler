package org.lastfm.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.Track;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to get Album and track number using MusicBrainz
 */

public class TrackFinder extends Track {
	private List<Track> trackList;
	private List<Release> release;
	private Map<String, Integer> compilation = new HashMap<String, Integer>();
	private static final Log log = LogFactory.getLog(TrackFinder.class);

	public String getAlbum(String artistName, String trackName) throws ServerUnavailableException {
		String album = "";
		trackList = Track.findByTitle(trackName);
		if (!trackList.isEmpty()) {
			log.debug("Getting album for track: " + trackName);
			for (Track track : trackList) {
				if (artistName.equals(track.getArtist().getName())) {
					log.debug("Artist: " + track.getArtist().getName());
					release = track.getReleases().getReleases();
					String trackNumber = (release.get(0).getTrackList().getOffset()).trim();
					log.debug("trackNumber: " + Integer.parseInt(trackNumber) + 1);
					album = release.get(0).getTitle();
					compilation.put(album, Integer.parseInt(trackNumber));
					break;
				}
			}
		}
		return album;
	}

	public int getTrackNumber(String album) {
		// TrackList offset starts in 0
		return compilation.get(album) + 1;
	}
}
