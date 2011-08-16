package org.lastfm.metadata;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 * @undestands This class knows how to read metadata from a mp3 file
 *
 */

public class Mp3Reader extends MetadataReader {
	private AudioFile audioFile;
	private AudioFileHelper audioFileHelper = new AudioFileHelper();

	public Metadata getMetadata(File file) throws CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, MetadataException {
		audioFile = audioFileHelper.getAudioFile(file);
		if (audioFile instanceof MP3File) {
			MP3File audioMP3 = (MP3File) audioFile;
			if (!audioMP3.hasID3v2Tag()) {
				AbstractID3v2Tag id3v2tag = new ID3v24Tag();
				audioMP3.setID3v2TagOnly(id3v2tag);
				try {
					audioFile.commit();
				} catch (CannotWriteException cwe) {
					log.error("An error occurs when I tried to update to ID3 v2");
					cwe.printStackTrace();
				}
			}
			tag = audioFile.getTag();
			header = audioFile.getAudioHeader();
			return generateMetadata(file);
		}
		return new Metadata();
	}
}
