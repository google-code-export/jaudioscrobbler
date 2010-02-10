package org.lastfm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;
import net.roarsoftware.lastfm.scrobble.Source;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class HelperScrobbler {
	private static final int DELTA = 120;
	private static final int MARGIN = 10;
	private int pendingFiles;

	Map<Metadata, Long> metadataMap;

	@SuppressWarnings("unused")
	private List<File> fileList;
	private ScrobblerFactory factory;

	private Logger log = Logger.getLogger("org.lastfm");

	public HelperScrobbler() {
		metadataMap = new HashMap<Metadata, Long>();
		factory = new ScrobblerFactory();
	}
	
	private int scrobbling(Metadata metadata) throws IOException,
			InterruptedException {
		Scrobbler scrobbler = factory.getScrobbler("tst", "1.0", ApplicationState.userName);
		ResponseStatus status = scrobbler.handshake(ApplicationState.password);
		
		if(status.getStatus() == ResponseStatus.OK){
			// According to Caching Rule (http://www.u-mass.de/lastfm/doc)
			Thread.sleep(200);
			
			status = scrobbler.submit(metadata.getArtist(), metadata.getTitle(),
					metadata.getAlbum(), metadata.getLength(), metadata
					.getTrackNumber(), Source.USER, metadataMap.get(
							metadata).longValue());
			if (!status.ok()) {
				log.error("Submitting track " + metadata.getTitle()
						+ " to Last.fm failed: " + status.getStatus());
			} else {
				log.debug(metadata.getArtist() + " - " + metadata.getTitle()
						+ " scrobbling to Last.fm was Successful");
			}
			return ApplicationState.OK;
		} else {
			System.err.println("Invalid Password");
			return ApplicationState.FAILURE;
		}
	}

	public List<Metadata> getMetadataList(List<File> fileList)
			throws InterruptedException, IOException, CannotReadException,
			TagException, ReadOnlyFileException, InvalidAudioFrameException,
			InvalidId3VersionException {
		this.fileList = fileList;
		List<Metadata> metadataList;
		Metadata metadata = null;
		metadataList = new ArrayList<Metadata>();

		for (File file : fileList) {
			if (file.getPath().endsWith("mp3")) {
				metadata = new MetadataMp3(file);
			} else if (file.getPath().endsWith("m4a")) {
				metadata = new MetadataMp4(file);
			}

			if (metadata == null) {
				log
						.error(file.getAbsoluteFile()
								+ " is not a valid Audio File");
			} else if (StringUtils.isNotEmpty(metadata.getArtist())
					&& StringUtils.isNotEmpty(metadata.getTitle())) {
				metadataList.add(metadata);
				ApplicationState.update(metadata);
			}
		}
		pendingFiles = metadataList.size() + MARGIN;
		return metadataList;
	}

	public void send(Metadata metadata) throws IOException,
			InterruptedException {
		
		long time = (System.currentTimeMillis() / 1000);

		// According to Submission rules http://www.last.fm/api/submissions
		if (StringUtils.isNotEmpty(metadata.getArtist())
				&& StringUtils.isNotEmpty(metadata.getTitle())
				&& metadata.getLength() > 240) {
			long startTime = time - (pendingFiles * DELTA);
			metadataMap.put(metadata, startTime);
			scrobbling(metadata);
			pendingFiles--;
		}
	}
}
