package org.lastfm.metadata;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
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
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class MetadataWriter {
	private String album;
	private Tag tag;
	private AudioFile audioFile;
	private String trackNumber;
	private String artist;
	private String trackName;

	public MetadataWriter() {
	}

	public MetadataWriter(File file, AudioFile audioFile) {
		this.audioFile = audioFile;
		tag = audioFile.getTag();
	}

	public void setFile(File file) {
		try {
			audioFile = AudioFileIO.read(file);
			tag = audioFile.getTag();
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}

	
	public void writeArtist(String artist) {
		this.artist = artist;
		try {
			tag.setField(FieldKey.ARTIST, artist);
			audioFile.commit();
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		} catch (CannotWriteException e) {
			e.printStackTrace();
		}
	}
	
	public void writeTrackName(String trackName) {
		this.trackName = trackName;
		try {
			tag.setField(FieldKey.TITLE, trackName);
			audioFile.commit();
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		} catch (CannotWriteException e) {
			e.printStackTrace();
		}
	}

	public void writeAlbum(String album) {
		this.album = album;
		try {
			tag.setField(FieldKey.ALBUM, album);
			audioFile.commit();
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		} catch (CannotWriteException e) {
			e.printStackTrace();
		}
	}
	public void writeTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
		try {
			tag.setField(FieldKey.TRACK, trackNumber);
			audioFile.commit();
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		} catch (CannotWriteException e) {
			e.printStackTrace();
		}
	}
	
	public String getAlbum() {
		return album;
	}

	public String getTrackNumber() {
		return trackNumber;
	}
}
