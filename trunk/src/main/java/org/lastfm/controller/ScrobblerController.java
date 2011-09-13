package org.lastfm.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.ActionResult;
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
	
	@PostConstruct
	public void setup() {
		helperScrobbler.setControlEngine(configurator.getControlEngine());
	}

	@RequestMethod(Actions.SEND_METADATA)
	public ActionResult sendMetadata(Metadata metadata) {
		try {
			log.info("Sending scrobbling for: " + metadata.getTitle());
			return helperScrobbler.send(metadata);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		} catch (InterruptedException ine) {
			log.error(ine, ine);
		}
		return ActionResult.FAILURE;
	}

}
