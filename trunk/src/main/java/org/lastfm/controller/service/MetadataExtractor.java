package org.lastfm.controller.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.lastfm.model.Model;
import org.lastfm.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who know how extract metadata from files using a root directory
 */

@Service
public class MetadataExtractor {
	private List<Metadata> metadataList;
	private Set<File> filesWithoutMinimumMetadata;
	private FileUtils fileUtils = new FileUtils();
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ControlEngineConfigurator configurator;

	public List<Metadata> extractMetadata(File root) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException, MetadataException {
		metadataList = new ArrayList<Metadata>();
		filesWithoutMinimumMetadata = new HashSet<File>();
		List<File> fileList = fileUtils.getFileList(root);
		configurator.getControlEngine().set(Model.FILES_WITHOUT_MINIMUM_METADATA, filesWithoutMinimumMetadata, null);
		return getMetadataList(fileList);
	}

	private List<Metadata> getMetadataList(List<File> fileList) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException,
			InvalidId3VersionException, MetadataException {

		for (File file : fileList) {
			Metadata metadata = null;
			if (fileUtils.isMp3File(file)) {
				metadata = new Mp3Reader().getMetadata(file);
			} else if (fileUtils.isM4aFile(file)) {
				metadata = new Mp4Reader().getMetadata(file);
			}

			if (metadata == null) {
				log.info(file.getAbsoluteFile() + " is not a valid Audio File");
			} else if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle())) {
				metadataList.add(metadata);
				configurator.getControlEngine().fireEvent(Events.LOAD, new ValueEvent<Metadata>(metadata));
			} else {
				log.info("I can not load the file: " + file.getName() + " because it doesn't contain miminum metadata");
				filesWithoutMinimumMetadata.add(file);
			}
		}
		return metadataList;
	}

}
