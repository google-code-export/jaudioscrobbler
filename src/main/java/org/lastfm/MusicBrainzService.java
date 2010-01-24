package org.lastfm;

import org.apache.commons.lang.StringUtils;

import com.slychief.javamusicbrainz.ServerUnavailableException;


public class MusicBrainzService {

	private TrackService trackService;

	public MusicBrainzService() {
		trackService = new TrackService();
	}
	
	public String getArtist(String artistName, String trackName) throws ServerUnavailableException {
		if(StringUtils.isEmpty(artistName) || StringUtils.isEmpty(trackName)){
			return "";
		} else {
			return trackService.getAlbum(artistName, trackName);
		}
	}

	public void setTrackService(TrackService trackService) {
		this.trackService = trackService;
	}


}
