package org.lastfm.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.action.ActionResult;
import org.lastfm.action.control.ControlEngine;
import org.lastfm.metadata.Metadata;
import org.lastfm.model.Model;
import org.lastfm.model.User;

import de.umass.lastfm.Session;
import de.umass.lastfm.scrobble.ScrobbleResult;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to send scrobblings
 */

public class ScrobblerHelper {
	private static final int ONE_THOUSAND = 1000;
	private static final int MIN_LENGHT = 240;
	private static final int REQUEST_PERIOD = 250;
	private Map<Metadata, Long> metadataMap = new HashMap<Metadata, Long>();
	private LastFMTrackHelper lastFMTrackHelper = new LastFMTrackHelper();
	private ScheduledExecutorService  scheduler = Executors.newSingleThreadScheduledExecutor();
	private static final int DELTA = 120;

	private Log log = LogFactory.getLog(this.getClass());
	private ControlEngine controlEngine;

	private ActionResult scrobbling(Metadata metadata) throws IOException, InterruptedException {
		User currentUser = controlEngine.get(Model.CURRENT_USER);
		if (StringUtils.isEmpty(currentUser.getUsername())) {
			return ActionResult.LOGGED_OUT;
		}

		if (currentUser.getSession() != null) {
			try {
				// According to Caching Rule (http://www.u-mass.de/lastfm/doc)
				ScheduledFuture<ActionResult> future = scheduler.schedule(new ScrobbleTask(metadata, currentUser.getSession()), REQUEST_PERIOD, TimeUnit.MICROSECONDS);
				return future.get();
			} catch (ExecutionException eex) {
				log.error(eex, eex);
				return ActionResult.FAILURE;
			}
			
		} else {
			log.error("There isn't a valid session");
			return ActionResult.SESSIONLESS;
		}
	}

	public ActionResult send(Metadata metadata) throws IOException, InterruptedException {
		long time = (System.currentTimeMillis() / ONE_THOUSAND);

		// According to submission rules http://www.last.fm/api/submissions
		if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle()) && metadata.getLength() > MIN_LENGHT) {
			long startTime = time - (metadataMap.size() * DELTA);
			metadataMap.put(metadata, startTime);
			return scrobbling(metadata);
		}
		return ActionResult.NOT_SCROBBLEABLE;
	}

	public void setControlEngine(ControlEngine controlEngine) {
		this.controlEngine = controlEngine;
	}
	
	private class ScrobbleTask implements Callable<ActionResult> {
		private final Metadata metadata;
		private final Session session;

		public ScrobbleTask(Metadata metadata, Session session) {
			this.metadata = metadata;
			this.session = session;
		}

		@Override
		public ActionResult call() throws Exception {
			ScrobbleResult result = lastFMTrackHelper.scrobble(metadata.getArtist(), metadata.getTitle(), metadataMap.get(metadata).intValue(), session);
			if (result.isSuccessful() && !result.isIgnored()) {
				log.debug(metadata.getArtist() + " - " + metadata.getTitle() + " scrobbling to Last.fm was Successful");
				return ActionResult.SUCCESS;
			} else {
				log.error("Submitting track " + metadata.getTitle() + " to Last.fm failed: " + result);
				return ActionResult.FAILURE;
			}
		}
	}
}
