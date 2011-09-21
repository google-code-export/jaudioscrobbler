package org.lastfm.helper;

import java.util.List;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.Track;

public class TrackHelper {

	public List<Track> findByTitle(String trackName) throws ServerUnavailableException {
		return Track.findByTitle(trackName);
	}

	public List<Release> getReleases(Track track) {
		return track.getReleases().getReleases();
	}

	public String getTrackNumber(Track track) {
		List<Release> releases = getReleases(track);
		return (releases.get(0).getTrackList().getOffset()).trim();
	}
	
	public int getTotalTrackNumber(Track track) throws ServerUnavailableException{
		List<Release> releases = getReleases(track);
		return releases.get(0).getTracks().size();
	}

	public String getAlbum(Track track) {
		List<Release> releases = getReleases(track);
		return releases.get(0).getTitle();
	}

	public String getArtist(Track track) {
		return track.getArtist().getName();
	}

}
