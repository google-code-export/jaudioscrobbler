package org.lastfm.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.lastfm.ApplicationState;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ActionMethod;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.controller.service.MetadataExtractor;
import org.lastfm.event.Events;
import org.lastfm.event.ValueEvent;
import org.lastfm.exception.InvalidId3VersionException;
import org.lastfm.metadata.MetadataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to load metadata from files
 */

@Controller
public class MetadataController {
	private JFileChooser fileChooser = new JFileChooser();
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private ControlEngineConfigurator configurator;
	@Autowired
	private MetadataExtractor metadataExtractor;

	@ActionMethod(Actions.GET_METADATA)
	public void getMetadata() {
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int selection = fileChooser.showOpenDialog(null);
		if (selection == JFileChooser.APPROVE_OPTION) {
			File root = fileChooser.getSelectedFile();
			configurator.getControlEngine().fireEvent(Events.DIRECTORY_SELECTED, new ValueEvent<String>(root.getAbsolutePath()));
			try {
				ApplicationState.metadataList = metadataExtractor.extractMetadata(root);
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

	private void handleException(Exception e) {
		log.error(e, e);
		configurator.getControlEngine().fireEvent(Events.OPEN);
	}
}
