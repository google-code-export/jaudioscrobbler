package org.lastfm.metadata;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who knows how to write metadata in a audio file
 */

public class MetadataWriter {
	private Tag tag;
	private AudioFile audioFile;
	private AudioFileHelper audioFileIOHelper = new AudioFileHelper();
	private Log log = LogFactory.getLog(this.getClass());

	public void setFile(File file) {
		try {
			audioFile = audioFileIOHelper.read(file);
			tag = audioFile.getTag();
		} catch (CannotReadException nre) {
			log.error(nre, nre);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		} catch (TagException tae) {
			log.error(tae, tae);
		} catch (ReadOnlyFileException roe) {
			log.error(roe, roe);
		} catch (InvalidAudioFrameException iae) {
			log.error(iae, iae);
		}
	}
	
	public void writeArtist(String artist) {
		try {
			tag.setField(FieldKey.ARTIST, artist);
			audioFile.commit();
		} catch (KeyNotFoundException kne) {
			log.error(kne, kne);
		} catch (FieldDataInvalidException fie) {
			log.error(fie, fie);
		} catch (CannotWriteException nwe) {
			log.error(nwe, nwe);
		}
	}
	
	public void writeTrackName(String trackName) {
		try {
			tag.setField(FieldKey.TITLE, trackName);
			audioFile.commit();
		} catch (KeyNotFoundException kne) {
			log.error(kne, kne);
		} catch (FieldDataInvalidException fie) {
			log.error(fie, fie);
		} catch (CannotWriteException nwe) {
			log.error(nwe, nwe);
		}
	}

	public boolean writeAlbum(String album) throws MetadataException {
		try {
			tag.setField(FieldKey.ALBUM, album);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		}
	}
	public boolean writeTrackNumber(String trackNumber) throws MetadataException {
		try {
			tag.setField(FieldKey.TRACK, trackNumber);
			audioFile.commit();
			return true;
		} catch (KeyNotFoundException kne) {
			throw new MetadataException(kne.getMessage());
		} catch (FieldDataInvalidException fie) {
			throw new MetadataException(fie.getMessage());
		} catch (CannotWriteException nwe) {
			throw new MetadataException(nwe.getMessage());
		}
	}
}