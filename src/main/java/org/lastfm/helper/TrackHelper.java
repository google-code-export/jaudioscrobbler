package org.lastfm.helper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.Track;

public class TrackHelper {
	private static final int FIRST_RELEASE = 21;
	private static final String ZERO = "0";
	private final Log log = LogFactory.getLog(this.getClass());

	public List<Track> findByTitle(String trackName) throws ServerUnavailableException {
		return Track.findByTitle(trackName);
	}

	public List<Release> getReleases(Track track) {
		List<Release> releases = track.getReleases().getReleases();
		for (Release release : releases) {
			log.debug("release: " + release.getTitle() + " # : " + releases.indexOf(release));
		}
		return releases;
	}

	public String getTrackNumber(Track track) {
		List<Release> releases = getReleases(track);
		return (releases.get(FIRST_RELEASE).getTrackList().getOffset()).trim();
	}
	
	public int getTotalTrackNumber(Track track) throws ServerUnavailableException{
		List<Release> releases = getReleases(track);
		return releases.get(FIRST_RELEASE).getTracks().size();
	}

	public String getAlbum(Track track) {
		List<Release> releases = getReleases(track);
		return releases.get(FIRST_RELEASE).getTitle();
	}

	public String getArtist(Track track) {
		return track.getArtist().getName();
	}

	public String getCdNumber(Track track) {
		return ZERO;
	}

	public String getTotalCds(Track track) {
		return ZERO;
	}
	public String getMusicBrainzID(Track track) {
		return track.getId();
	}
}
