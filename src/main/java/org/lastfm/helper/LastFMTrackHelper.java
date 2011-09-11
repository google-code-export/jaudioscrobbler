package org.lastfm.helper;

import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleResult;

public class LastFMTrackHelper {

	public ScrobbleResult scrobble(String artist, String title, int time, Session session) {
		return Track.scrobble(artist, title, time, session);
	}

}
