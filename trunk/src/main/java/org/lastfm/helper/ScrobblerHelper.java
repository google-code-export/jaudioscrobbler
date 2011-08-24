package org.lastfm.helper;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;
import net.roarsoftware.lastfm.scrobble.Source;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;
import org.lastfm.ScrobblerFactory;
import org.lastfm.metadata.Metadata;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to send scrobblings
 */

public class ScrobblerHelper {
	private Map<Metadata, Long> metadataMap = new HashMap<Metadata, Long>();
	private ScrobblerFactory factory = new ScrobblerFactory();
	private static final int DELTA = 120;

	private Log log = LogFactory.getLog(this.getClass()); 

	private int scrobbling(Metadata metadata) throws IOException, InterruptedException {
		if(StringUtils.isEmpty(ApplicationState.username)){
			return ApplicationState.LOGGED_OUT;
		}
		Scrobbler scrobbler = factory.getScrobbler(ApplicationState.CLIENT_SCROBBLER_ID, ApplicationState.CLIENT_SCROBBLER_VERSION, ApplicationState.username);
		ResponseStatus status = scrobbler.handshake(ApplicationState.password);

		if (status.getStatus() == ResponseStatus.OK) {
			// According to Caching Rule (http://www.u-mass.de/lastfm/doc)
			Thread.sleep(200);

			try{
				status = scrobbler.submit(metadata.getArtist(), metadata.getTitle(), metadata.getAlbum(), metadata
						.getLength(), metadata.getTrackNumber(), Source.USER, metadataMap.get(metadata).longValue());
				if (status.getStatus() == ResponseStatus.OK) {
					log.debug(metadata.getArtist() + " - " + metadata.getTitle() + " scrobbling to Last.fm was Successful");
					return ApplicationState.OK;
				} else {
					log.error("Submitting track " + metadata.getTitle() + " to Last.fm failed: " + status.getStatus());
					return ApplicationState.FAILURE;
				}
			} catch (UnknownHostException uhe){
				log.error(uhe, uhe);
				return ApplicationState.ERROR;
			} catch (ConnectException coe){
				log.error(coe, coe);
				return ApplicationState.ERROR;
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
		if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle())
				&& metadata.getLength() > 240) {
			long startTime = time - (metadataMap.size() * DELTA);
			metadataMap.put(metadata, startTime);
			result = scrobbling(metadata);
			if(result == ApplicationState.ERROR){
				return result;
			}
		}
		return result;
	}
}
