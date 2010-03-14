package org.lastfm;

import net.roarsoftware.lastfm.scrobble.Scrobbler;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class ScrobblerFactory {
	
	public Scrobbler getScrobbler(String clientId, String clientVersion, String user){
		return Scrobbler.newScrobbler(clientId, clientVersion, user);
	}
	
}
