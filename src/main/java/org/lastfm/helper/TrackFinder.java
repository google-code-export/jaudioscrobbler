package org.lastfm.helper;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.model.MusicBrainzTrack;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Track;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to get Album and track number using MusicBrainz
 */

public class TrackFinder extends Track {
	private List<Track> trackList;
	private int trackNumber;
	private TrackHelper trackHelper = new TrackHelper();
	private static final Log log = LogFactory.getLog(TrackFinder.class);

	public synchronized MusicBrainzTrack getAlbum(String artist, String trackname) throws ServerUnavailableException {
		MusicBrainzTrack musicBrainzTrack = new MusicBrainzTrack();
		String album = new String();
		trackList = trackHelper.findByTitle(trackname);
		if (!trackList.isEmpty()) {
			log.debug("Getting album for track: " + trackname);
			for (Track track : trackList) {
				String artistFromMusicBrainz = trackHelper.getArtist(track);
				if (artist.equals(artistFromMusicBrainz)) {
					log.debug("Artist: " + artistFromMusicBrainz);
					String trackNumberAsString = trackHelper.getTrackNumber(track);
					log.debug("trackNumber: " + Integer.parseInt(trackNumberAsString) + 1);
					trackNumber = Integer.parseInt(trackNumberAsString) + 1;
					album = trackHelper.getAlbum(track);
					int totalTrackNumber = trackHelper.getTotalTrackNumber(track);
					log.debug("totalTrackNumber: " + totalTrackNumber);
					musicBrainzTrack.setAlbum(album);
					musicBrainzTrack.setTrackNumber(trackNumber);
					musicBrainzTrack.setTotalTrackNumber(totalTrackNumber);
					break;
				}
			}
		}
		return musicBrainzTrack;
	}
}
