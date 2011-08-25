package org.lastfm.helper;

import net.roarsoftware.lastfm.scrobble.Scrobbler;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who create a single instance from Scrobbler factory
 */

public class ScrobblerSingleton {
	private Scrobbler scrobbler;

	public Scrobbler getScrobbler(String clientId, String clientVersion, String user){
		if(scrobbler == null){
			scrobbler = Scrobbler.newScrobbler(clientId, clientVersion, user);
		}
		return scrobbler;
	}
	
}
