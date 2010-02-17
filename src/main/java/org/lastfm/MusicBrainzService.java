package org.lastfm;

import org.apache.commons.lang.StringUtils;

import com.slychief.javamusicbrainz.ServerUnavailableException;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class MusicBrainzService {

	private TrackService trackService;

	public MusicBrainzService() {
		trackService = new TrackService();
	}
	
	public String getAlbum(String artistName, String trackName) throws ServerUnavailableException {
		if(StringUtils.isEmpty(artistName) || StringUtils.isEmpty(trackName)){
			return "";
		} else {
			return trackService.getAlbum(artistName, trackName);
		}
	}

	public void setTrackService(TrackService trackService) {
		this.trackService = trackService;
	}

	public int getTrackNumber(String album) {
		return trackService.getTrackNumber(album);
	}


}
