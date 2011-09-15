package org.lastfm.controller.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.event.Events;
import org.lastfm.event.ValueEvent;
import org.lastfm.exception.InvalidId3VersionException;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataException;
import org.lastfm.metadata.Mp3Reader;
import org.lastfm.metadata.Mp4Reader;
import org.lastfm.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who know how extract metadata from files using a root directory
 */

@Service
public class MetadataExtractor {
	private List<Metadata> metadataList = new ArrayList<Metadata>();
	private FileUtils fileUtils = new FileUtils();
	
	@Autowired
	private ControlEngineConfigurator configurator;

	public List<Metadata> extractMetadata(File root) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException, MetadataException {
		List<File> fileList = fileUtils.getFileList(root);
		return getMetadataList(fileList);
	}

	private List<Metadata> getMetadataList(List<File> fileList) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException,
			InvalidId3VersionException, MetadataException {

		Metadata metadata = null;
		for (File file : fileList) {
			if (file.getPath().endsWith("mp3")) {
				metadata = new Mp3Reader().getMetadata(file);
			} else if (file.getPath().endsWith("m4a")) {
				metadata = new Mp4Reader().getMetadata(file);
			}

			if (metadata == null) {
				throw new MetadataException(file.getAbsoluteFile() + " is not a valid Audio File");
			} else if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle())) {
				metadataList.add(metadata);
				configurator.getControlEngine().fireEvent(Events.LOAD, new ValueEvent<Metadata>(metadata));
			}
		}
		return metadataList;
	}

}
