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

public class LoginController {
	ScrobblerFactory factory;
	
	public LoginController() {
		factory = new ScrobblerFactory();
	}

	public int login(String username, String password) throws IOException {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			return ApplicationState.ERROR;
		}
		Scrobbler scrobbler = factory.getScrobbler("tst", "1.0", username);
		ResponseStatus status = scrobbler.handshake(password);
		return status.getStatus();
	}

}
