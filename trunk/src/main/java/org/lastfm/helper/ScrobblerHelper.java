package org.lastfm.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.Model;
import org.lastfm.model.User;

import de.umass.lastfm.scrobble.ScrobbleResult;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to send scrobblings
 */

public class ScrobblerHelper {
	private Map<Metadata, Long> metadataMap = new HashMap<Metadata, Long>();
	private LastFMTrackHelper lastFMTrackHelper = new LastFMTrackHelper();
	private static final int DELTA = 120;

	private Log log = LogFactory.getLog(this.getClass());
	private ControlEngine controlEngine;

	private int scrobbling(Metadata metadata) throws IOException, InterruptedException {
		User currentUser = controlEngine.get(Model.CURRENT_USER);
		if (StringUtils.isEmpty(currentUser.getUsername())) {
			return ApplicationState.LOGGED_OUT;
		}

		if (currentUser.getSession() != null) {
			// According to Caching Rule (http://www.u-mass.de/lastfm/doc)
			Thread.sleep(200);

			ScrobbleResult result = lastFMTrackHelper.scrobble(metadata.getArtist(), metadata.getTitle(), metadataMap.get(metadata).intValue(), currentUser.getSession());
			if (result.isSuccessful() && !result.isIgnored()) {
				log.debug(metadata.getArtist() + " - " + metadata.getTitle() + " scrobbling to Last.fm was Successful");
				return ApplicationState.OK;
			} else {
				log.error("Submitting track " + metadata.getTitle() + " to Last.fm failed: " + result);
				return ApplicationState.FAILURE;
			}
		} else {
			System.err.println("error at scrobbling");
			log.error(ApplicationState.HAND_SHAKE_FAIL);
			return ApplicationState.FAILURE;
		}
	}

	public int send(Metadata metadata) throws IOException, InterruptedException {
		int result = ApplicationState.FAILURE;
		long time = (System.currentTimeMillis() / 1000);

		// According to submission rules http://www.last.fm/api/submissions
		if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle()) && metadata.getLength() > 240) {
			long startTime = time - (metadataMap.size() * DELTA);
			metadataMap.put(metadata, startTime);
			result = scrobbling(metadata);
			if (result == ApplicationState.ERROR) {
				return result;
			}
		}
		return result;
	}

	public void setControlEngine(ControlEngine controlEngine) {
		this.controlEngine = controlEngine;
	}
}
