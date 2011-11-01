package org.lastfm.helper;

import org.apache.commons.lang3.StringUtils;
import org.lastfm.model.MusicBrainzTrack;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to get Album and track number using MusicBrainz in the top abstraction level
 */

public class MusicBrainzDelegator {
	private TrackFinder trackFinder = new TrackFinder();

	public MusicBrainzTrack getAlbum(String artistName, String trackName) throws ServerUnavailableException {
		if(StringUtils.isEmpty(artistName) || StringUtils.isEmpty(trackName)){
			return new MusicBrainzTrack();
		} else {
			return trackFinder.getAlbum(artistName, trackName);
		}
	}
}
