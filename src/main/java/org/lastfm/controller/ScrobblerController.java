package org.lastfm.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.action.control.RequestMethod;
import org.lastfm.helper.ScrobblerHelper;
import org.lastfm.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who manage ALL scrobbler actions
 */

@Controller
public class ScrobblerController {
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private ScrobblerHelper helperScrobbler;
	@Autowired
	private ControlEngineConfigurator configurator;

	@RequestMethod(Actions.SEND_METADATA)
	public Integer sendMetadata(Metadata metadata) {
		int result = ApplicationState.OK;
		try {
			result = helperScrobbler.send(metadata, configurator);
			log.info("Sending scrobbling for: " + metadata.getTitle());
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		} catch (InterruptedException ine) {
			log.error(ine, ine);
		}
		return result;
	}

}
