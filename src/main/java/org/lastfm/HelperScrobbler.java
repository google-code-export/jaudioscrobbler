package org.lastfm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;
import net.roarsoftware.lastfm.scrobble.Source;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class HelperScrobbler {
	private static final int DELTA = 120;

	Map<Metadata, Long> metadataMap;

	@SuppressWarnings("unused")
	private List<File> fileList;
	ScrobblerFactory factory;

	private Logger log = Logger.getLogger("org.lastfm");

	public HelperScrobbler() {
		metadataMap = new HashMap<Metadata, Long>();
		factory = new ScrobblerFactory();
	}

	private int scrobbling(Metadata metadata) throws IOException, InterruptedException {
		Scrobbler scrobbler = factory.getScrobbler("tst", "1.0", ApplicationState.userName);
		ResponseStatus status = scrobbler.handshake(ApplicationState.password);

		if (status.getStatus() == ResponseStatus.OK) {
			// According to Caching Rule (http://www.u-mass.de/lastfm/doc)
			Thread.sleep(200);

			status = scrobbler.submit(metadata.getArtist(), metadata.getTitle(), metadata.getAlbum(), metadata
					.getLength(), metadata.getTrackNumber(), Source.USER, metadataMap.get(metadata).longValue());
			if (status.getStatus() == ResponseStatus.OK) {
				log.debug(metadata.getArtist() + " - " + metadata.getTitle() + " scrobbling to Last.fm was Successful");
				return ApplicationState.OK;
			} else {
				log.error("Submitting track " + metadata.getTitle() + " to Last.fm failed: " + status.getStatus());
				return ApplicationState.FAILURE;
			}
		} else {
			log.error(ApplicationState.HAND_SHAKE_FAIL);
			return ApplicationState.FAILURE;
		}
	}

	public int send(Metadata metadata) throws IOException, InterruptedException {
		int result = ApplicationState.FAILURE;
		long time = (System.currentTimeMillis() / 1000);

		// According to Submission rules http://www.last.fm/api/submissions
		if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle())
				&& metadata.getLength() > 240) {
			long startTime = time - (metadataMap.size() * DELTA);
			metadataMap.put(metadata, startTime);
			result = scrobbling(metadata);
		}
		return result;
	}
}
