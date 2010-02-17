package org.lastfm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.slychief.javamusicbrainz.ServerUnavailableException;
import com.slychief.javamusicbrainz.entities.Release;
import com.slychief.javamusicbrainz.entities.Track;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class TrackService extends Track {
	List<Track> trackList;
	List<Release> release;
	Map<String, Integer> compilation = new HashMap<String, Integer>();

	public String getAlbum(String artistName, String trackName)
			throws ServerUnavailableException {
		System.out.println("--------------------------------------------");
		String album = "";
		trackList = Track.findByTitle(trackName);
		if (!trackList.isEmpty()) {
			System.out.println(trackName);
			for (Track track : trackList) {
				if (artistName.equals(track.getArtist().getName())) {
					System.out.println(track.getArtist().getName());
					release = track.getReleases().getReleases();
					String trackNumber = (release.get(0).getTrackList().getOffset()).trim();
					System.out.println(Integer.parseInt(trackNumber) + 1);
					album = release.get(0).getTitle();
					compilation.put(album, Integer.parseInt(trackNumber));
					break;
				}
			}
		}
		return album;
	}

	public int getTrackNumber(String album) {
		//TrackList offset starts in 0 
		return compilation.get(album) + 1;
	}

}
