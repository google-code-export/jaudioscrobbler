package org.lastfm.helper;

import org.apache.commons.lang.StringUtils;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to get Album and track number using MusicBrainz in the top abstraction level
 */

public class MusicBrainzDelegator {
	private TrackFinder trackFinder = new TrackFinder();

	public String getAlbum(String artistName, String trackName) throws ServerUnavailableException {
		if(StringUtils.isEmpty(artistName) || StringUtils.isEmpty(trackName)){
			return "";
		} else {
			return trackFinder.getAlbum(artistName, trackName);
		}
	}

	public int getTrackNumber(String album) {
		return trackFinder.getTrackNumber(album);
	}

}
