package org.lastfm.helper;

import java.util.List;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.Track;

public class TrackHelper {
	private static final int FIRST_RELEASE = 0;
	private static final String ONE = "1";

	public List<Track> findByTitle(String trackName) throws ServerUnavailableException {
		return Track.findByTitle(trackName);
	}

	public List<Release> getReleases(Track track) {
		List<Release> releases = track.getReleases().getReleases();
		return releases;
	}

	public String getTrackNumber(Track track) {
		List<Release> releases = getReleases(track);
		return (releases.get(FIRST_RELEASE).getTrackList().getOffset()).trim();
	}

	public int getTotalTrackNumber(Track track) throws ServerUnavailableException {
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
		return ONE;
	}

	public String getTotalCds(Track track) {
		return ONE;
	}

	public String getMusicBrainzID(Track track) {
		return track.getId();
	}
}
