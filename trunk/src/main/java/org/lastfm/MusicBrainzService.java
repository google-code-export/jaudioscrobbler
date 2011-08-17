package org.lastfm;

import org.apache.commons.lang.StringUtils;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to get Album and track number using Music Brainz 
 */

public class MusicBrainzService {
	private TrackService trackService = new TrackService();

	public String getAlbum(String artistName, String trackName) throws ServerUnavailableException {
		if(StringUtils.isEmpty(artistName) || StringUtils.isEmpty(trackName)){
			return "";
		} else {
			return trackService.getAlbum(artistName, trackName);
		}
	}

	public int getTrackNumber(String album) {
		return trackService.getTrackNumber(album);
	}

}
