package org.lastfm.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;
import org.lastfm.HelperScrobbler;
import org.lastfm.action.Actions;
import org.lastfm.action.control.RequestMethod;
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
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	public void setAddHelperScrobbler(HelperScrobbler helperScrobbler) {
		this.helperScrobbler = helperScrobbler;
	}

	@SuppressWarnings("unused")
	@RequestMethod(Actions.SEND_METADATA)
	private Integer sendMetadata(Metadata metadata) {
		int result = ApplicationState.OK;
		try {
			result = helperScrobbler.send(metadata);
			log.info("Sending scrobbling for: " + metadata.getTitle());
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		} catch (InterruptedException ine) {
			log.error(ine, ine);
		}
		return result;
	}

}
