package org.lastfm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.ApplicationState;
import org.lastfm.InvalidId3VersionException;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ActionMethod;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.event.Events;
import org.lastfm.event.ValueEvent;
import org.lastfm.metadata.Metadata;
import org.lastfm.metadata.MetadataException;
import org.lastfm.metadata.Mp3Reader;
import org.lastfm.metadata.Mp4Reader;
import org.lastfm.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to load metadata from files
 */

@Controller
public class MetadataController {
	private JFileChooser fileChooser = new JFileChooser();
	private FileUtils fileUtils = new FileUtils();
	private ControlEngineConfigurator configurator;
	private List<Metadata> metadataList = new ArrayList<Metadata>();
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	public void setAddConfigurator(ControlEngineConfigurator configurator) {
		this.configurator = configurator;
	}

	@ActionMethod(Actions.GET_METADATA)
	public void showMetadata() {
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int selection = fileChooser.showOpenDialog(null);
		if (selection == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			configurator.getControlEngine().fireEvent(Events.DIRECTORY_SELECTED,
					new ValueEvent<String>(file.getAbsolutePath()));
			try {
				showFiles(file);
				configurator.getControlEngine().fireEvent(Events.LOADED);
			} catch (IOException e) {
				handleException(e);
			} catch (TagException e) {
				handleException(e);
			} catch (ReadOnlyFileException e) {
				handleException(e);
			} catch (InvalidAudioFrameException e) {
				handleException(e);
			} catch (InvalidId3VersionException e) {
				handleException(e);
			} catch (InterruptedException e) {
				handleException(e);
			} catch (CannotReadException e) {
				handleException(e);
			} catch (MetadataException e) {
				handleException(e);
			}
		}
	}

	private int showFiles(File root) throws InterruptedException, IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, InvalidId3VersionException, CannotReadException, MetadataException {
		List<File> fileList = fileUtils.getFileList(root);
		metadataList = getMetadataList(fileList);
		ApplicationState.metadataList = metadataList;
		return ApplicationState.OK;
	}

	private List<Metadata> getMetadataList(List<File> fileList) throws InterruptedException, IOException,
			CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException,
			InvalidId3VersionException, MetadataException {

		Metadata metadata = null;
		for (File file : fileList) {
			if (file.getPath().endsWith("mp3")) {
				metadata = new Mp3Reader().getMetadata(file);
			} else if (file.getPath().endsWith("m4a")) {
				metadata = new Mp4Reader().getMetadata(file);
			}

			if (metadata == null) {
				log.error(file.getAbsoluteFile() + " is not a valid Audio File");
			} else if (StringUtils.isNotEmpty(metadata.getArtist()) && StringUtils.isNotEmpty(metadata.getTitle())) {
				metadataList.add(metadata);
				configurator.getControlEngine().fireEvent(Events.LOAD, new ValueEvent<Metadata>(metadata));
			}
		}
		return metadataList;
	}
	
	private void handleException(Exception e) {
		log.error(e, e);
		configurator.getControlEngine().fireEvent(Events.OPEN);
	}
}
