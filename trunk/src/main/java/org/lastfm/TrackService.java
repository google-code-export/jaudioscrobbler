package org.lastfm;

import java.util.List;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.Track;

public class TrackService extends Track {
	List<Track> trackList;
	List<Release> release;

	public String getAlbum(String artistName, String trackName) throws ServerUnavailableException {
		String album = "";
		trackList = Track.findByTitle(trackName);
		if (!trackList.isEmpty()) {
			System.out.println(trackName);
			for (Track track : trackList) {
				if (artistName.equals(track.getArtist().getName())) {
					System.out.println(track.getArtist().getName());
					release = track.getReleases().getReleases();
					for (Release rel : release) {
						album = rel.getTitle();
						System.out.println(album);
					}
				}
			}
		}
		return album;
	}

}
