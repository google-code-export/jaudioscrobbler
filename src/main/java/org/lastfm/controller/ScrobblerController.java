package org.lastfm.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.lastfm.ApplicationState;
import org.lastfm.HelperScrobbler;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ActionMethod;
import org.lastfm.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who manage ALL scrobbler actions
 */

@Controller
public class ScrobblerController {
	private HelperScrobbler helperScrobbler;
	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	public void setAddHelperScrobbler(HelperScrobbler helperScrobbler) {
		this.helperScrobbler = helperScrobbler;
	}

	@SuppressWarnings("unused")
	@ActionMethod(Actions.SEND_METADATA)
	private Metadata sendMetadata(Metadata metadata) {
		int result;
		try {
			result = helperScrobbler.send(metadata);
			if (result == ApplicationState.ERROR) {
				metadata.setSendStatus(ApplicationState.ERROR);
			}
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		} catch (InterruptedException ine) {
			log.error(ine, ine);
		}
		return metadata;
	}

}
