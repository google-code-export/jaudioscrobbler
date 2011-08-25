package org.lastfm.helper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Track;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to get Album and track number using MusicBrainz
 */

public class TrackFinder extends Track {
	private List<Track> trackList;
	private int tracknumber;
	private TrackHelper trackHelper = new TrackHelper();
	private static final Log log = LogFactory.getLog(TrackFinder.class);

	public String getAlbum(String artist, String trackname) throws ServerUnavailableException {
		String album = new String();
		trackList = trackHelper.findByTitle(trackname);
		if (!trackList.isEmpty()) {
			log.debug("Getting album for track: " + trackname);
			for (Track track : trackList) {
				String artistFromMusicBrainz = trackHelper.getArtist(track);
				if (artist.equals(artistFromMusicBrainz)) {
					log.debug("Artist: " + artistFromMusicBrainz);
					String trackNumber = trackHelper.getTrackNumber(track);
					log.debug("trackNumber: " + Integer.parseInt(trackNumber) + 1);
					tracknumber = Integer.parseInt(trackNumber) + 1;
					album = trackHelper.getAlbum(track); 
					break;
				}
			}
		}
		return album;
	}

	public int getTrackNumber(String album) {
		return tracknumber;
	}
}
