package org.lastfm;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

public class LastFMAuthenticator {
	private ScrobblerFactory factory = new ScrobblerFactory();
	
	public int login(String username, String password) throws IOException {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			return ApplicationState.ERROR;
		}
		Scrobbler scrobbler = factory.getScrobbler(ApplicationState.CLIENT_SCROBBLER_ID, ApplicationState.CLIENT_SCROBBLER_VERSION, username);
		ResponseStatus status = scrobbler.handshake(password);
		return status.getStatus();
	}

}
