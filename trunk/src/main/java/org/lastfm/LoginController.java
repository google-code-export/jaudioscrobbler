package org.lastfm;

import java.io.IOException;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;

public class LoginController {
	ScrobblerFactory factory;
	
	public LoginController() {
		factory = new ScrobblerFactory();
	}

	public int login(String username, String password) throws IOException {
		Scrobbler scrobbler = factory.getScrobbler("tst", "1.0", username);
		ResponseStatus status = scrobbler.handshake(password);
		return status.getStatus();
	}

}
