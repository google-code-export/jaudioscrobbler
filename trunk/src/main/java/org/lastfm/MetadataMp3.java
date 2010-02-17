package org.lastfm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;

/**
 * 
 * @author Jose Luis De la Cruz
 *
 */

public class MetadataMp3 extends Metadata {
	private Tag tag;
	private AudioHeader header;
	private File file;
	private AudioFile audioFile;

	public MetadataMp3(File file) throws FileNotFoundException, IOException,
			CannotReadException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException, InvalidId3VersionException {
		this.file = file;
		audioFile = AudioFileIO.read(file);
		getMetadata();
	}

	// For Mocking proposes
	public MetadataMp3(File file, AudioFile audioFile)
			throws FileNotFoundException, IOException, CannotReadException,
			TagException, ReadOnlyFileException, InvalidAudioFrameException,
			InvalidId3VersionException {
		this.audioFile = audioFile;
		this.file = file;
		getMetadata();
	}

	public void getMetadata() throws CannotReadException, IOException,
			TagException, ReadOnlyFileException, InvalidAudioFrameException,
			InvalidId3VersionException {
		if (audioFile instanceof MP3File) {
			MP3File audioMP3 = (MP3File) audioFile;
			if (!audioMP3.hasID3v2Tag()) {
				AbstractID3v2Tag id3v2tag = new ID3v24Tag();
				audioMP3.setID3v2TagOnly(id3v2tag);
				try {
					audioFile.commit();
				} catch (CannotWriteException cwe) {
					this.getLog().error(
							"An error occurs when I tried to update to ID3 v2");
					cwe.printStackTrace();
				}
			} else {
				tag = audioFile.getTag();
				header = audioFile.getAudioHeader();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lastfm.Metadata#getArtist()
	 */
	public String getArtist() {
		return tag.getFirst(FieldKey.ARTIST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lastfm.Metadata#getTitle()
	 */
	public String getTitle() {
		return tag.getFirst(FieldKey.TITLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lastfm.Metadata#getAlbum()
	 */
	public String getAlbum() {
		return tag.getFirst(FieldKey.ALBUM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lastfm.Metadata#getSize()
	 */
	public int getLength() {
		return header.getTrackLength();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lastfm.Metadata#getTrackNumber()
	 */
	public int getTrackNumber() {
		int trackNumber = -1;
		try {
			trackNumber = Integer.parseInt(tag.getFirst(FieldKey.TRACK));
		} catch (NullPointerException nue) {
		} catch (NumberFormatException nfe) {
			trackNumber = -1;
			this.getLog().error(
					file.getAbsolutePath() + " has a not valid Track Number");
		}
		return trackNumber;
	}
}
